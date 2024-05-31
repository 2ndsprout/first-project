package com.example.demo_project.diary.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create (String username, String password, String nickname, String email) {
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(passwordEncoder.encode(password));
        member.setNickname(nickname);
        member.setEmail(email);
        member.setCreateDate(LocalDateTime.now());

        return this.memberRepository.save(member);
    }

    public Member getMember (String username) {
        Optional<Member> _member = this.memberRepository.findByUsername(username);
        if (_member.isEmpty()) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return _member.get();
    }
    public void changeNickname (String username, String nickname) {
        Member member = this.getMember(username);
        member.setNickname(nickname);
        this.memberRepository.save(member);
    }

    public void save (Member member) {
        this.memberRepository.save(member);
    }

    public List<Member> findAllMembers() {
        List<Member> memberList = this.memberRepository.findAll();
        return memberList.stream()
                .filter(member -> !"admin".equals(member.getUsername()))
                .collect(Collectors.toList());
    }
}
