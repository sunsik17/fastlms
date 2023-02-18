package com.zerobase.fastlms.banner.dto;

import com.zerobase.fastlms.banner.entity.Banner;
import lombok.*;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BannerDto {
    Long id;

    String bannerName;
    String pathOnClick;
    String target;
    int sortNum;
    boolean isPosting;
    String createdAt;

    long totalCount;
    long seq;

    String fileName;
    String urlName;

    public static BannerDto fromEntity(Banner banner){
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .pathOnClick(banner.getPathOnClick())
                .sortNum(banner.getSortNum())
                .isPosting(banner.isPosting())
                .fileName(banner.getFileName())
                .urlName(banner.getUrlName())
                .createdAt(banner.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }
}
