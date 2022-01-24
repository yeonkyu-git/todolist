package yeonkyu.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Comment;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.domain.Todo;
import yeonkyu.todolist.repository.CommentRepository;
import yeonkyu.todolist.repository.MemberRepository;
import yeonkyu.todolist.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;

    /**
     * 코멘트 등록
     */
    @Transactional
    public Long createComment(Long memberId, Long todoId, String body) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Todo> todo = todoRepository.findById(todoId);

        if (member.isPresent() && todo.isPresent()) {
            Comment comment = new Comment(body, member.get(), todo.get());
            commentRepository.save(comment);
            return comment.getId();
        }

        throw new IllegalStateException("비정상적인 접근입니다.");  // 이렇게 Exception 터트리는게 맞을까?
    }

    /**
     * 코멘트 조회
     */
    public List<Comment> findComment(Long memberId, Long todoId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Todo> todo = todoRepository.findById(todoId);

        if (member.isPresent() && todo.isPresent()) {
            return commentRepository.findCommentByMemberAndTodo(member.get(), todo.get());
        }

        throw new IllegalStateException("비정상적인 접근입니다.");  // 이렇게 Exception 터트리는게 맞을까?
    }

    /**
     * 코멘트 수정
     */
    @Transactional
    public void updateComment(Long commentId, String body) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isPresent()) {
            comment.orElse(null).updateComment(body);
        } else {
            throw new IllegalStateException("비정상적인 접근입니다.");  // 이렇게 Exception 터트리는게 맞을까?
        }
    }


    /**
     * 코멘트 삭제
     */
    @Transactional
    public void deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if (comment.isPresent()) {
            commentRepository.delete(comment.orElse(null));
        } else {
            throw new IllegalStateException("비정상적인 접근입니다.");  // 이렇게 Exception 터트리는게 맞을까?
        }
    }


}
