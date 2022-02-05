package yeonkyu.todolist.controller.dto;

import lombok.Getter;
import yeonkyu.todolist.domain.TodoCategory;
import yeonkyu.todolist.domain.TodoStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TodoFormList {

    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private List<String> categoryName = new ArrayList<>();
    private TodoStatus status;


    public TodoFormList(Long id,
                        String title,
                        String description,
                        LocalDate deadline,
                        TodoStatus status,
                        List<TodoCategory> todoCategories) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;

        for (TodoCategory todoCategory : todoCategories) {
            setCategoryName(todoCategory);
        }
    }

    public void setCategoryName(TodoCategory todoCategory) {
        categoryName.add(todoCategory.getCategory().getCategoryName());
    }
}
