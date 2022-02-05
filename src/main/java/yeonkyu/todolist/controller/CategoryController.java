package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yeonkyu.todolist.controller.dto.CategoryForm;
import yeonkyu.todolist.controller.dto.CategoryListForm;
import yeonkyu.todolist.controller.dto.SessionConst;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.repository.CategoryRepository;
import yeonkyu.todolist.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

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
     * 카테고리 리스트 페이지
     */
    @GetMapping("/category/list")
    public String showCategory(Model model, HttpServletRequest request) {

        // 현재 로그인된 멤버 아이디 불러오기
        Long memberId = (Long) request.getAttribute("memberId");
        if (memberId == null) {
            log.info("잘못된 경로");
            return "redirect:/";
        }

        List<CategoryListForm> categoryFormList = new ArrayList<>();
        categoryService.findByCategoryWithMember(memberId).stream()
                .forEach(c -> categoryFormList.add(new CategoryListForm(c.getId(), c.getCategoryName())));

        for (CategoryListForm categoryForm : categoryFormList) {
            System.out.println("categoryForm = " + categoryForm.getCategoryName());
        }

        model.addAttribute("categoryFormList", categoryFormList);
        return "category/categoryList";
    }

    /**
     * 카테고리 수정
     */
    @PostMapping("/category/revise/{id}")
    public String reviseCategory(@PathVariable("id") Long id,
                                 @RequestParam("categoryName") String categoryName) {
        categoryService.updateCategory(id, categoryName);
        return "redirect:/category/list";
    }

    /**
     * 카테고리 삭제
     */
    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/category/list";
    }
}


