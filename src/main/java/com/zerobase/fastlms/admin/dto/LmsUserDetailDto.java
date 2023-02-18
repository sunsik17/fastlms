package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.LmsUser;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LmsUserDetailDto {
    Long id;
    LocalDateTime loginAt;
    String clientIp;
    String userAgent;

    public static LmsUserDetailDto fromEntity(LmsUser lmsUser){
        return LmsUserDetailDto.builder()
                .id(lmsUser.getId())
                .loginAt(lmsUser.getLoginAt())
                .clientIp(lmsUser.getClientIp())
                .userAgent(lmsUser.getUserAgent())
                .build();
    }
}
