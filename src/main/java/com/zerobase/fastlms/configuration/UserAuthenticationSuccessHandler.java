package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.admin.entity.LmsUser;
import com.zerobase.fastlms.admin.repository.LoginLmsUsersRepository;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final LoginLmsUsersRepository loginLmsUsersRepository;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        loginLmsUsersRepository.save(LmsUser.builder()
                .username(request.getParameter("username"))
                .userAgent(request.getHeader("user-agent"))
                .loginAt(LocalDateTime.now())
                .clientIp(request.getRemoteAddr())
                .build());

        Optional<Member> optionalMember = memberRepository.findByUserId(request.getParameter("username"));

        Member member = optionalMember.get();
        member.setLoginAt(LocalDateTime.now());

        memberRepository.save(member);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
