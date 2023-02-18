package com.zerobase.fastlms.banner.controller;

import com.zerobase.fastlms.util.PageUtil;

public class BaseController {
    public String getPaperHtml(long totalCount,
                               long pageSize,
                               long pageIdx,
                               String query) {
        return new PageUtil(totalCount, pageSize, pageIdx, query).pager();
    }
}
