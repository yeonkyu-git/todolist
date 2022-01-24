package yeonkyu.todolist.controller.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class TodoForm {

    @NotNull
    private Long categoryId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate notification;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;


    public TodoForm(Long categoryId, LocalDate deadline, LocalDate notification, String title, String description) {
        this.categoryId = categoryId;
        this.deadline = deadline;
        this.notification = notification;
        this.title = title;
        this.description = description;
    }
}
