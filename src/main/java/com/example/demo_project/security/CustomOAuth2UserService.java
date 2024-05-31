package com.example.demo_project.security;

import com.example.demo_project.diary.member.Member;
import com.example.demo_project.diary.member.MemberRepository;
import com.example.demo_project.diary.member.OAuth2Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        OAuth2Member oAuth2Member;
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String usernameKey;
        List<GrantedAuthority> authorities = new ArrayList<>();

        switch (registrationId) {
            case "google":
                oAuth2Member = this.googleService(user, registrationId);
                usernameKey = "sub";
                break;
            case "naver":
                oAuth2Member = this.naverService(user, registrationId);
                usernameKey = "response";
                break;
            case "kakao":
                oAuth2Member = this.kakaoService(user, registrationId);
                usernameKey = "id";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + registrationId);
        }
        Optional<Member> _member = this.memberRepository.findByUsername(oAuth2Member.getUsername());
        Member member = new Member();

        if (_member.isPresent()) {
            member = _member.get();
            System.out.println("기존 회원 로그인");
            authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        }
        else {
            member.setUsername(oAuth2Member.getUsername());
            member.setPassword(oAuth2Member.getPassword());
            member.setNickname(oAuth2Member.getNickname());
            member.setEmail(oAuth2Member.getEmail());
            member.setCreateDate(LocalDateTime.now());
            this.memberRepository.save(member);
            authorities.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
        }
        return new DefaultOAuth2User(authorities, user.getAttributes(), usernameKey);
    }

    public OAuth2Member googleService (OAuth2User user, String registrationId) {
        String username = user.getAttribute("sub");
        String password = "";
        String nickname = registrationId + "_" + user.getAttribute("name");
        String email = user.getAttribute("email");

        return new OAuth2Member(username, password, nickname, email);
    }

    public OAuth2Member naverService (OAuth2User user, String registrationId) {

        Map<String, Object> response = user.getAttribute("response");
        assert response != null;
        String username = (String) response.get("id");
        String password = "";
        String nickname = registrationId + "_" + (String) response.get("nickname");
        String email = (String) response.get("email");

        return new OAuth2Member(username, password, nickname, email);
    }

    public OAuth2Member kakaoService (OAuth2User user, String registrationId) {
        Map<String, Object> kakaoAccount = user.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String username = user.getAttribute("id").toString();
        String password = "";
        String nickname = registrationId + "_" + (String) profile.get("nickname");
        String email = (String) kakaoAccount.get("email");

        return new OAuth2Member(username, password, nickname, email);
    }
}
