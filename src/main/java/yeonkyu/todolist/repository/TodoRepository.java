package yeonkyu.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.domain.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findTodoByMember(Member member);
}
