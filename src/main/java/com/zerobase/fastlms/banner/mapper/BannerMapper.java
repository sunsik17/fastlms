package com.zerobase.fastlms.banner.mapper;

import com.zerobase.fastlms.banner.entity.Banner;
import com.zerobase.fastlms.banner.model.BannerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {
    long totalCount(BannerParam param);

    List<Banner> selectList(BannerParam param);
}
