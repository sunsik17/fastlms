package com.zerobase.fastlms.main.controller;


import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.service.AdminBannerService;
import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponents mailComponents;
    private final AdminBannerService adminBannerService;
    @RequestMapping("/")
    public String index(Model model) {

        List<BannerDto> postingBanners = adminBannerService.getPostedBanner();

        postingBanners.sort((x, y) -> x.getSortNum() - y.getSortNum());
        System.out.println(postingBanners.get(0).isPosting());
        System.out.println(postingBanners.size());

        model.addAttribute("list", postingBanners);
        
        return "index";
    }
    
    
    
    @RequestMapping("/error/denied")
    public String errorDenied() {
        
        return "error/denied";
    }
    
    
    
}
