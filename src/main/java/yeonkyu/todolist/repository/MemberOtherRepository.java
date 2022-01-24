package yeonkyu.todolist.repository;

import yeonkyu.todolist.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberOtherRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();

}
