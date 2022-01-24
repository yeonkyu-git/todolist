package yeonkyu.todolist.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yeonkyu.todolist.domain.audit.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Category(String categoryName, Member member) {
        this.categoryName = categoryName;
        this.member = member;
    }

    //== 비즈니스 로직 ==//
    public void updateCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
