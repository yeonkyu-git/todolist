package yeonkyu.todolist.controller.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
public class LoginForm {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
