package yeonkyu.todolist.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.domain.Todo;
import yeonkyu.todolist.domain.TodoStatus;
import yeonkyu.todolist.repository.TodoRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TodoServiceTest {

    @Autowired
    TodoService todoService;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    CategoryService categoryService;

    @PersistenceContext
    EntityManager em;

    @Test
    public void 할일등록() throws Exception {
        //given
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        memberService.join(member);
        Long categoryId = categoryService.createCategory(member.getId(), "운동");

        //when
        Long todoId = todoService.createTodo("Spring공부", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
        Todo todo = todoRepository.findById(todoId).orElse(null);

        //then
        Assertions.assertThat(todo.getTitle()).isEqualTo("Spring공부");
        Assertions.assertThat(todo.getDescription()).isEqualTo("Spring data");
    }

    @Test
    public void 할일_수정() throws Exception {
        //given
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        em.persist(member);
        Long categoryId = categoryService.createCategory(member.getId(), "운동");

        //when
        Long todoId = todoService.createTodo("Spring공부", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
        Todo todo = todoRepository.findById(todoId).orElse(null);
        todo.updateTodo("Spring!!!!", "Spring MVC", LocalDate.now());

        em.flush();
        em.clear();

        Todo findTodo = todoRepository.findById(todoId).orElse(null);

        //then
        Assertions.assertThat(todo.getTitle()).isEqualTo("Spring!!!!");
        Assertions.assertThat(todo.getDescription()).isEqualTo("Spring MVC");
    }

    @Test
    public void 완료처리() throws Exception {
        //given
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        em.persist(member);
        Long categoryId = categoryService.createCategory(member.getId(), "운동");

        //when
        Long todoId = todoService.createTodo("Spring공부", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
        Todo todo = todoRepository.findById(todoId).orElse(null);
        todo.updateTodoStatus();

        em.flush();
        em.clear();

        Todo findTodo = todoRepository.findById(todoId).orElse(null);

        //then
        Assertions.assertThat(todo.getStatus()).isEqualTo(TodoStatus.COMPLETE);
    }

    @Test
    public void 할일삭제() throws Exception {
        //given
        Member member = new Member("김연규", "dusrbpoiiij@naver.com", "test!", "010-8547-2613");
        em.persist(member);
        Long categoryId = categoryService.createCategory(member.getId(), "운동");

        //when
        Long todoId = todoService.createTodo("Spring공부", "Spring data", LocalDate.now(), LocalDate.now(), member.getId(), categoryId);
        Todo todo = todoRepository.findById(todoId).orElse(null);
        em.flush();
        em.clear();

        todoService.deleteTodo(todoId);
        Optional<Todo> findTodo = todoRepository.findById(todoId);

        //then
        Assertions.assertThat(findTodo).isEmpty();

    }

}