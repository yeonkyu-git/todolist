package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import yeonkyu.todolist.controller.dto.AddTodoCategoryList;
import yeonkyu.todolist.controller.dto.CategoryForm;
import yeonkyu.todolist.controller.dto.SessionConst;
import yeonkyu.todolist.controller.dto.TodoForm;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.service.CategoryService;
import yeonkyu.todolist.service.TodoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;
    private final CategoryService categoryService;


    /**
     * todolist 화면
     */
    @GetMapping("/todolist")
    public String todoList(@ModelAttribute("categoryForm") CategoryForm categoryForm) {
        return "todo/todolist";
    }


    /**
     * todo 등록 화면 불러오기
     */
    @GetMapping("/todolist/add")
    public String todoAddForm(@ModelAttribute("todoForm") TodoForm todoForm,
                          Model model,
                          HttpServletRequest request) {

        //  /todolist/add 로 들어오면 Interceptor에서 CategoryNames를 만들고 request에 담아서 던져준다.
        model.addAttribute("categoryNames", request.getAttribute("categoryNames"));
        return "todo/addTodo";
    }


    /**
     * todo 등록
     */
    @PostMapping("/todolist/add")
    public String todoAdd(@Validated @ModelAttribute("todoForm") TodoForm todoForm,
                          BindingResult bindingResult,
                          HttpServletRequest request) {

        Long memberId = (Long) request.getAttribute("memberId");

        if (memberId == null) {
            log.info("멤버 아이디 NULL");
            return "redirect:/todolist";
        }

        if (bindingResult.hasErrors()) {
            return "todo/addTodo";
        }

        todoService.createTodo(todoForm.getTitle(),
                todoForm.getDescription(),
                todoForm.getDeadline(),
                todoForm.getNotification(),
                memberId,
                todoForm.getCategoryId());

        log.info("투두 등록 완료");

        return "redirect:/todolist";
    }
}
