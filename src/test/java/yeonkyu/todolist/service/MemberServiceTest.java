package yeonkyu.todolist.service;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.repository.jdbctemplate.MemberJdbcRepository;
import yeonkyu.todolist.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberJdbcRepository memberJdbcRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = createMember();

        //when
        Member savedMember = memberService.join(member);
//        memberJdbcRepository.save(member);

        Member findMember = memberRepository.findByEmail(member.getEmail()).orElse(null);

        //then
        Assertions.assertThat(findMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    public void 중복회원가입() throws Exception {
        //given
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.join(member1);

        //when
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

        //then
    }


    static Member createMember() {
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        return member;
    }

}