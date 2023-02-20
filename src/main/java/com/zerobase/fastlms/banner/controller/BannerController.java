package com.zerobase.fastlms.banner.controller;

import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.banner.service.AdminBannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BannerController extends BaseController {
    private final AdminBannerService adminBannerService;

    @GetMapping("/admin/banner/list.do")
    public String list(Model model, BannerParam param) {
        param.init();

        List<BannerDto> bannerDtoList =
                adminBannerService.adminBannerList(
                        param, adminBannerService.findAll()
                );

        long totalCount = bannerDtoList.size();

        String query = param.getQueryString();
        String pagerHtml =
                getPaperHtml(totalCount,
                        param.getPageSize(),
                        param.getPageIndex(),
                        query);

        model.addAttribute("list", bannerDtoList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "/admin/banner/list";
    }

    @GetMapping({"/admin/banner/add.do", "admin/banner/edit.do"})
    public String addBanner(Model model, HttpServletRequest request,
                            BannerInput param) {

        boolean editMode = request.getRequestURI().contains("/edit.do");
        BannerDto bannerDto = new BannerDto();

        if (editMode) {
            long id = param.getId();
            BannerDto existBanner = adminBannerService.getById(id);
            if (existBanner == null) {
                model.addAttribute("message", "배너 정보가 존재하지 않습니다.");
                return "common/error";
            }
            bannerDto = existBanner;
        }
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", bannerDto);

        return "admin/banner/add";
    }

    @PostMapping({"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request,
                            MultipartFile file,
                            BannerInput param) {

        String saveFilename = "";
        String urlFilename = "";

        if (file != null) {
            String originalFilename = file.getOriginalFilename();

            String baseLocalPath = "/Users/nss/Documents/zb_project/fastlms3/src/main/resources/static";
            String baseUrlPath = "";

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);
            System.out.println("========================" + Arrays.toString(arrFilename));
            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1].replaceFirst("/", "");

            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("############################ - 1");
                log.info(e.getMessage());
            }
        }
        param.setFileName(saveFilename);
        param.setUrlName(urlFilename);
        param.setBannerName(request.getParameter("bannerName"));
        param.setPosting(
                request.getParameterValues("isPosting") != null);
        param.setTarget(request.getParameter("targetSelect"));

        boolean editMode = request.getRequestURI().contains("/edit.do");

        if (editMode){
            long id = param.getId();
            BannerDto existBanner = adminBannerService.getById(id);
            if (existBanner == null){
                model.addAttribute("message", "배너정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = adminBannerService.setBanner(param);
        } else {
            boolean result = adminBannerService.addBanner(param);
        }
        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/admin/banner/delete.do")
    public String del(Model model, HttpServletRequest request,
                      BannerInput param){

        boolean result = adminBannerService.delete(param.getIdList());

        return "redirect:/admin/banner/list.do";
    }




    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        return new String[]{newFilename, newUrlFilename};
    }
}
