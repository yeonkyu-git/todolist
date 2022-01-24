package yeonkyu.todolist.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class CategoryForm {

    @NotEmpty
    private String categoryName;

    public CategoryForm(String categoryName) {
        this.categoryName = categoryName;
    }
}
