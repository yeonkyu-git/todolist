package yeonkyu.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yeonkyu.todolist.domain.Comment;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.domain.Todo;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentByMemberAndTodo(Member member, Todo todo);
}
