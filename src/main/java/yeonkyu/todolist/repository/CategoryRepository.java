package yeonkyu.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findCategoryByMember(Member member);
}
