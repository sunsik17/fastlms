package com.zerobase.fastlms.banner.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerInput {
    Long id;

    String bannerName;
    String pathOnClick;
    String target;
    int sortNum;
    boolean isPosting;
    String createdAt;

    long totalCount;
    long seq;

    String idList;

    String fileName;
    String urlName;
}
