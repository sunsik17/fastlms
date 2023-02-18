package com.zerobase.fastlms.banner.service;

import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;

import java.util.List;

public interface AdminBannerService {

    boolean addBanner(BannerInput param);
    boolean setBanner(BannerInput param);
    List<BannerDto> adminBannerList(BannerParam param, List<Banner> banners);
    BannerDto getById(Long id);
    boolean delete(String idList);
    List<BannerDto> getPostedBanner();
    List<Banner> findAll();
}
