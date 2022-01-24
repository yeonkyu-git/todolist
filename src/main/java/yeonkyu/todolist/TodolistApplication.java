package yeonkyu.todolist;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import yeonkyu.todolist.controller.dto.SessionConst;
import yeonkyu.todolist.domain.Member;
import yeonkyu.todolist.repository.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class TodolistApplication {

	private final HttpServletRequest request;
	private final MemberRepository memberRepository;

	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return () -> Optional.of(UUID.randomUUID().toString());
	}

}
