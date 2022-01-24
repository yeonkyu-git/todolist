package yeonkyu.todolist.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeonkyu.todolist.domain.audit.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "todo_id")
    private Long id;

    private String title;
    private String description;
    private LocalDate deadline;
    private LocalDate notification;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    private List<TodoCategory> todoCategories = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public Todo(String title, String description, LocalDate deadline, LocalDate notification, Member member) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.notification = notification;
        this.member = member;
        this.status = TodoStatus.DOING;
    }

    //== 생성 메서드 ==//
    public static Todo createTodo(String title,
                                  String description,
                                  LocalDate deadline,
                                  LocalDate notification,
                                  TodoCategory todoCategory,
                                  Member member) {
        Todo todo = new Todo(title, description, deadline, notification, member);
        todo.addTodoCategory(todoCategory);
        return todo;
    }


    //== 연관관계 메소드 ==//
    public void addTodoCategory(TodoCategory todoCategory) {
        this.todoCategories.add(todoCategory);
        todoCategory.setTodo(this);
    }

    //== 비즈니스 메소드 ==//
    public void updateTodo(String title, String description, LocalDate deadline) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
    }

    public void updateTodoStatus() {
        status = (status == TodoStatus.DOING) ? TodoStatus.COMPLETE : TodoStatus.DOING;
    }


}
