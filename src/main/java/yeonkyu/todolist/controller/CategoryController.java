package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import yeonkyu.todolist.controller.dto.CategoryForm;
import yeonkyu.todolist.controller.dto.SessionConst;
import yeonkyu.todolist.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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


        if (bindingResult.hasErrors()) {
            log.info("카테고리 입력 값 : {}", categoryForm.getCategoryName());
            return "todo/todolist";
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("카테고리 등록. 잘못된 경로");
            return "redirect:/";
        }
        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 카테고리 등록
        Long categoryId = categoryService.createCategory(memberId, categoryForm.getCategoryName());

        log.info("카테고리 정상 등록 완료 : {}", categoryId);

        return "redirect:/todolist";
    }
}
