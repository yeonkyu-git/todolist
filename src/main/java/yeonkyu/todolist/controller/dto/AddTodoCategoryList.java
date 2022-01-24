package yeonkyu.todolist.controller.dto;

import lombok.Getter;

@Getter
public class AddTodoCategoryList {

    private Long id;
    private String categoryName;

    public AddTodoCategoryList(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
