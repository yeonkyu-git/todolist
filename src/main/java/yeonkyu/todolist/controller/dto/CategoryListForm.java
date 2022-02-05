package yeonkyu.todolist.controller.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class CategoryListForm {
    private Long id;

    @NotEmpty
    private String categoryName;

    public CategoryListForm(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
