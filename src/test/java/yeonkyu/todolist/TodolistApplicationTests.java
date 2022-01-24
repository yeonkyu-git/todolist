package yeonkyu.todolist;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.domain.QMember;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class TodolistApplicationTests {

	@Autowired
	EntityManager em;

	@Test
	void contextLoads() {

		Member member = new Member("kim", "dusrbpoiiij@naver.com", "123123", "insta");
		em.persist(member);

		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		QMember qMember = QMember.member;

		Member findMember = queryFactory
				.selectFrom(qMember)
				.where(qMember.name.eq("kim"))
				.fetchOne();


		Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());

	}

}
