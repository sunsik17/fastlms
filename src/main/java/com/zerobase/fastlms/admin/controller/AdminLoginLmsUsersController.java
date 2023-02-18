package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.service.LmsUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class AdminLoginLmsUsersController {

    private final LmsUserService lmsUserService;


//    @GetMapping()
//    public List<LmsUserDetailDto> userDetailHistory (LmsUser lmsUser){
//
//        return lmsUserService.findByUserName(lmsUser);
//    }

}
