package yeonkyu.todolist.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import yeonkyu.todolist.controller.dto.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor2 implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("인증체크 인터셉터 실행 로그인된 유저가 로그인창, 회원가입 창으로 갈 때 제어");

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
            log.info("로그인된 사용자가 로그인창 혹은 회원가입 창으로 가려 함 ");
            response.sendRedirect("/todolist");
            return false;
        }
        return true;
    }
}
