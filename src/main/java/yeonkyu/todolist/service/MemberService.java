package yeonkyu.todolist.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRespository;

    /**
     * 회원가입
     */
    @Transactional
    public Member join(Member member) {
        validationMember(member);
        Member savedMember = memberRespository.save(member);
        return savedMember;
    }

    private void validationMember(Member member) {
        Optional<Member> fineMember = memberRespository.findByEmail(member.getEmail());
        System.out.println("fineMember = " + fineMember);
        if (fineMember.isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    /**
     * 로그인
     */
    public Member login(String email, String password) {
        return memberRespository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }



}
