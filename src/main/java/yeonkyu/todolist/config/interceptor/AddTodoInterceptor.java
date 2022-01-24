package yeonkyu.todolist.config.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import yeonkyu.todolist.controller.dto.AddTodoCategoryList;
import yeonkyu.todolist.controller.dto.SessionConst;
import yeonkyu.todolist.service.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AddTodoInterceptor implements HandlerInterceptor {

    private final CategoryService categoryService;

    public AddTodoInterceptor(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 저장된 카테고리 이름 불러오기
        List<AddTodoCategoryList> categoryNames = new ArrayList<>();
        categoryService.findByCategoryWithMember(memberId).stream()
                .forEach(c -> categoryNames.add(new AddTodoCategoryList(c.getId(), c.getCategoryName())));
        request.setAttribute("categoryNames", categoryNames);

        return true;
    }
}
