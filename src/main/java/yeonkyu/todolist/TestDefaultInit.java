package yeonkyu.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.service.CategoryService;
import yeonkyu.todolist.service.MemberService;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDefaultInit {

    private final MemberService memberService;
    private final CategoryService categoryService;


    @PostConstruct
    public void init() {
        Member member = new Member("kim", "test@naver.com", "test!", "insta");
        memberService.join(member);


        categoryService.createCategory(member.getId(), "공부");
        categoryService.createCategory(member.getId(), "운동");
    }
}
