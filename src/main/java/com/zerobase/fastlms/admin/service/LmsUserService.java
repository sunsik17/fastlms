package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.LmsUserDetailDto;
import com.zerobase.fastlms.admin.repository.LoginLmsUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LmsUserService {

    private final LoginLmsUsersRepository loginLmsUsersRepository;

    public List<LmsUserDetailDto> findByUserName(String userName) {
        return loginLmsUsersRepository.findByUsername(userName)
                .stream()
                .map(LmsUserDetailDto::fromEntity)
                .collect(Collectors.toList());
    }

}
