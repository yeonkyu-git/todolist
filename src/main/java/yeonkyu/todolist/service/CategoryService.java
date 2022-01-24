package yeonkyu.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Category;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.repository.CategoryRepository;
import yeonkyu.todolist.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;


    /**
     * 카테고리 등록
     */
    @Transactional
    public Long createCategory(Long memberId, String categoryName) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Category category = new Category(categoryName, member);
        Category savedCategory = categoryRepository.save(category);
        return savedCategory.getId();
    }

    /**
     * 카테고리 조회
     */
    public List<Category> findByCategoryWithMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        return categoryRepository.findCategoryByMember(member);
    }

    /**
     * 카테고리 변경
     */
    @Transactional
    public void updateCategory(Long categoryId, String categoryName) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            category.orElse(null).updateCategoryName(categoryName);
        }
    }

    /**
     * 카테고리 삭제
     */
    @Transactional
    public void deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            categoryRepository.delete(category.orElse(null));
        }
    }
}
