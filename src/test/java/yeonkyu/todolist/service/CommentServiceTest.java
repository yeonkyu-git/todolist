package yeonkyu.todolist.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Comment;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.domain.Todo;
import yeonkyu.todolist.repository.CommentRepository;
import yeonkyu.todolist.repository.TodoRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    CommentService commentService;
    @Autowired
    MemberService memberService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    TodoService todoService;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    CommentRepository commentRepository;
    @PersistenceContext
    EntityManager em;


    @Test
    public void 코멘트_등록() throws Exception {
        //given
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        memberService.join(member);
        Long categoryId = categoryService.createCategory(member.getId(), "운동");
        Long todoId = todoService.createTodo("Spring공부", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
        Todo todo = todoRepository.findById(todoId).orElse(null);

        //when
        Long commentId = commentService.createComment(member.getId(), todo.getId(), "코멘트입니다.");
        Comment comment = commentRepository.findById(commentId).orElse(null);

        //then
        Assertions.assertThat(comment.getBody()).isEqualTo("코멘트입니다.");
    }

    @Test
    public void 코멘트조회() throws Exception {
        //given
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        memberService.join(member);
        Long categoryId = categoryService.createCategory(member.getId(), "운동");
        Long todoId = todoService.createTodo("Spring공부", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
        Long todoIdOther = todoService.createTodo("Spring시작", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
        Todo todo = todoRepository.findById(todoId).orElse(null);
        Todo todoOther = todoRepository.findById(todoIdOther).orElse(null);

        //when
        commentService.createComment(member.getId(), todo.getId(), "코멘트입니다1");
        commentService.createComment(member.getId(), todo.getId(), "코멘트입니다2");
        commentService.createComment(member.getId(), todo.getId(), "코멘트입니다3");

        List<Comment> comment = commentService.findComment(member.getId(), todo.getId());
        List<Comment> commentOther = commentService.findComment(member.getId(), todoOther.getId());

        //then
        Assertions.assertThat(comment.size()).isEqualTo(3);
        Assertions.assertThat(commentOther.size()).isEqualTo(0);
    }

    @Test
    public void 코멘트수정() throws Exception {
        //given
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        memberService.join(member);
        Long categoryId = categoryService.createCategory(member.getId(), "운동");
        Long todoId = todoService.createTodo("Spring공부", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
        Todo todo = todoRepository.findById(todoId).orElse(null);
        Long commentId = commentService.createComment(member.getId(), todo.getId(), "코멘트입니다1");

        //when
        commentService.updateComment(commentId, "코멘트입니다2");

        em.flush();
        em.clear();


        Comment comment = commentRepository.findById(commentId).orElse(null);

        //then
        Assertions.assertThat(comment.getBody()).isEqualTo("코멘트입니다2");
    }

    @Test
    public void 코멘트삭제() throws Exception {
        //given
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        memberService.join(member);
        Long categoryId = categoryService.createCategory(member.getId(), "운동");
        Long todoId = todoService.createTodo("Spring공부", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
        Todo todo = todoRepository.findById(todoId).orElse(null);
        Long commentId = commentService.createComment(member.getId(), todo.getId(), "코멘트입니다1");

        //when
        commentService.deleteComment(commentId);

        em.flush();
        em.clear();

        Optional<Comment> comment = commentRepository.findById(commentId);

        //then
        Assertions.assertThat(comment).isEmpty();

    }



}