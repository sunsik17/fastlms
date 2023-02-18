package com.zerobase.fastlms.banner.service;

import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.mapper.BannerMapper;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.banner.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminBannerServiceImpl implements AdminBannerService {
    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    @Override
    public boolean addBanner(BannerInput param) {
        bannerRepository.save(Banner.builder()
                .bannerName(param.getBannerName())
                .pathOnClick(param.getPathOnClick())
                .target(param.getTarget())
                .sortNum(param.getSortNum())
                .isPosting(param.isPosting())
                .createdAt(LocalDateTime.now())
                .fileName(param.getFileName())
                .urlName(param.getUrlName())
                .build());
        return true;
    }

    @Override
    public boolean setBanner(BannerInput param) {
        Optional<Banner> optionalBanner = bannerRepository.findById(param.getId());
        if (!optionalBanner.isPresent()) {
            return false;
        }
        Banner banner = optionalBanner.get();
        banner.setBannerName(param.getBannerName());
        banner.setPathOnClick(param.getPathOnClick());
        banner.setTarget(param.getTarget());
        banner.setSortNum(param.getSortNum());
        banner.setPosting(param.isPosting());
        banner.setFileName(param.getFileName());
        banner.setEditedAt(LocalDateTime.now());
        banner.setUrlName(param.getUrlName());

        bannerRepository.save(banner);
        return true;
    }

    @Override
    public List<BannerDto> adminBannerList(BannerParam param, List<Banner> banners) {
        List<Banner> bannerList = bannerMapper.selectList(param);

        long totalCount = bannerList.size();

        List<BannerDto> bannerDtoList =
                bannerList.stream().map(BannerDto::fromEntity)
                        .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(bannerDtoList)) {
            int i = 0;
            for (BannerDto bt : bannerDtoList) {
                bt.setTotalCount(bannerDtoList.size());
                bt.setSeq(totalCount - param.getPageStart() - i);
                i++;
            }
        }
        return bannerDtoList;
    }

    @Override
    public BannerDto getById(Long id) {
        Optional<Banner> optionalBanner = bannerRepository.findById(id);
        if (optionalBanner.isPresent()) {
            Banner banner = optionalBanner.get();
            return BannerDto.fromEntity(banner);
        }
        return null;
    }

    @Override
    public boolean delete(String num) {
        if (num != null && num.length() > 0) {
            String[] ids = num.split(",");
            for (String s : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id > 0) {
                    bannerRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public List<BannerDto> getPostedBanner() {
        return bannerRepository.findAllByIsPosting(true)
                .stream().map(BannerDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }
}
