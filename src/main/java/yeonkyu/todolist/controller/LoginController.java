package yeonkyu.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import yeonkyu.todolist.controller.dto.LoginForm;
import yeonkyu.todolist.controller.dto.SessionConst;
import yeonkyu.todolist.controller.dto.SignForm;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.service.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/todolist") String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = memberService.login(loginForm.getEmail(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        log.info("login Member : {}", loginMember.getName());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember.getId());

        return "redirect:" + redirectURL;
    }

    @GetMapping("/sign")
    public String signForm(@ModelAttribute("signForm") SignForm signForm) {
        return "login/signForm";
    }

    @PostMapping("/sign")
    public String sign(@Validated @ModelAttribute("signForm") SignForm signForm,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/login/signForm";
        }

        Member member = new Member(signForm.getName(), signForm.getEmail(), signForm.getPassword(), signForm.getSms());
        Member joinMember = memberService.join(member);
        log.info("join Member : {}", joinMember);

        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";

    }
}
