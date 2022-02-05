package yeonkyu.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.service.CategoryService;
import yeonkyu.todolist.service.MemberService;
import yeonkyu.todolist.service.TodoService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

//@Component
@RequiredArgsConstructor
public class TestDefaultInit {

    private final MemberService memberService;
    private final CategoryService categoryService;
    private final TodoService todoService;


    @PostConstruct
    public void init() {
        Member member = new Member("kim", "test@naver.com", "test!", "insta");
        memberService.join(member);


        Long categoryId = categoryService.createCategory(member.getId(), "공부");
        Long categoryId2 = categoryService.createCategory(member.getId(), "운동");
        createTodo(member, categoryId);
        createTodo(member, categoryId2);

    }

    void createTodo(Member member, Long categoryId) {
        todoService.createTodo("Spring공부", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
    }
}
