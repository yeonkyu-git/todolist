package yeonkyu.todolist.controller.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
public class SignForm {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String sms;

    public SignForm(String name, String email, String password, String sms) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.sms = sms;
    }
}
