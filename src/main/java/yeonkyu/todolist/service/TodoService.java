package yeonkyu.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.domain.Todo;
import yeonkyu.todolist.domain.TodoCategory;
import yeonkyu.todolist.repository.CategoryRepository;
import yeonkyu.todolist.repository.MemberRepository;
import yeonkyu.todolist.repository.TodoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;


    /**
     * 할일 등록
     */
    @Transactional
    public Long createTodo(String title,
                           String description,
                           LocalDate deadline,
                           LocalDate notification,
                           Long memberId,
                           Long categoryId) {

        // 엔티티 조회
        Member member = memberRepository.findById(memberId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);

        // 투두 카테고리 생성
        TodoCategory todoCategory = new TodoCategory(category);

        // 투두 생성
        Todo todo = Todo.createTodo(title, description, deadline, notification, todoCategory, member);

        todoRepository.save(todo);
        return todo.getId();
    }

    /**
     * 할일 조회
     */
    public List<Todo> findTodoByMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isPresent()) {
            return todoRepository.findTodoByMember(member.get());
        }
        return null;
    }


    /**
     * 할일 수정
     */
    @Transactional
    public void updateTodo(Long todoId,
                           String title,
                           String description,
                           LocalDate deadline) {
        Optional<Todo> todo = todoRepository.findById(todoId);

        if (todo.isPresent()) {
            todo.get().updateTodo(title, description, deadline);
        }
    }

    /**
     * 완료 처리
     */
    @Transactional
    public void updateTodoStatus(Long todoId) {
        Optional<Todo> todo = todoRepository.findById(todoId);

        if (todo.isPresent()) {
            todo.get().updateTodoStatus();
        }
    }

    /**
     * 할일 삭제
     */
    @Transactional
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElse(null);
        todoRepository.delete(todo);
    }
}
