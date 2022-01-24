package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import yeonkyu.todolist.controller.dto.CategoryForm;
import yeonkyu.todolist.controller.dto.SessionConst;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 등록
     */
    @PostMapping("/category/add")
    public String addCategory(@Validated @ModelAttribute("categoryForm") CategoryForm categoryForm,
                              BindingResult bindingResult,
                              HttpServletRequest request) {


        // 인렵값 검증
        if (bindingResult.hasErrors()) {
            log.info("카테고리 입력 값 : {}", categoryForm.getCategoryName());
            return "todo/todolist";
        }

        // 인터셉터에서 저장한 memberId 불러오기
        Long memberId = (Long) request.getAttribute("memberId");
        if (memberId == null) {
            log.info("잘못된 경로");
            return "redirect:/";
        }

        // 카테고리 등록
        Long categoryId = categoryService.createCategory(memberId, categoryForm.getCategoryName());
        log.info("카테고리 정상 등록 완료 : {}", categoryId);
        return "redirect:/todolist";
    }

    /**
     * 카테고리 조회
     */
    @GetMapping("/category/list")
    public String showCategory(HttpServletRequest request) {

        // 현재 로그인된 멤버 아이디 불러오기
        Long memberId = (Long) request.getAttribute("memberId");
        if (memberId == null) {
            log.info("잘못된 경로");
            return "redirect:/";
        }

        List<Category> categories = categoryService.findByCategoryWithMember(memberId);

        return "category/categoryList";
    }
}
