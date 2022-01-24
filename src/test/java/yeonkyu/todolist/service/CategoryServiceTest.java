package yeonkyu.todolist.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MemberService memberService;


    @Test
    public void 카테고리_등록() throws Exception {
        //given
        Member member = createMember();
        memberService.join(member);

        //when
        Long categoryId = categoryService.createCategory(member.getId(), "운동");
        Category category = categoryRepository.findById(categoryId).orElse(null);

        //then
        Assertions.assertThat(category.getCategoryName()).isEqualTo("운동");
    }

    @Test
    public void 카테고리_조회() throws Exception {
        //given
        Member member1 = createMember();
        Member member2 = new Member("김연규", "rbdusrla@naver.com", "test!", "010-8547-2613");
        memberService.join(member1);
        memberService.join(member2);

        //when
        categoryService.createCategory(member1.getId(), "운동");
        categoryService.createCategory(member1.getId(), "공부");
        categoryService.createCategory(member2.getId(), "생활");

        List<Category> categories1 = categoryService.findByCategoryWithMember(member1.getId());
        List<Category> categories2 = categoryService.findByCategoryWithMember(member2.getId());

        //then
        Assertions.assertThat(categories1.size()).isEqualTo(2);
        Assertions.assertThat(categories2.size()).isEqualTo(1);
    }

    @Test
    public void 카테고리_변경() throws Exception {
        //given
        Member member = createMember();
        memberService.join(member);

        //when
        Long categoryId = categoryService.createCategory(member.getId(), "운동");
        categoryService.updateCategory(categoryId, "공부");
        Category category = categoryRepository.findById(categoryId).orElse(null);

        //then
        Assertions.assertThat(category.getCategoryName()).isEqualTo("공부");
    }

    @Test
    public void 카테고리_삭제() throws Exception {
        //given
        Member member = createMember();
        memberService.join(member);

        //when
        Long categoryId = categoryService.createCategory(member.getId(), "운동");
        categoryService.deleteCategory(categoryId);
        Optional<Category> findCategory = categoryRepository.findById(categoryId);
        System.out.println("findCategory = " + findCategory);

        //then
        Assertions.assertThat(findCategory).isEmpty();

    }



    static Member createMember() {
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        return member;
    }
}