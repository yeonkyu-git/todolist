package yeonkyu.todolist.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import yeonkyu.todolist.config.filter.LogFilter;
import yeonkyu.todolist.config.interceptor.AddTodoInterceptor;
import yeonkyu.todolist.config.interceptor.LogInterceptor;
import yeonkyu.todolist.config.interceptor.LoginCheckInterceptor;
import yeonkyu.todolist.config.interceptor.LoginCheckInterceptor2;
import yeonkyu.todolist.service.CategoryService;

import javax.servlet.Filter;

@Configuration
@RequiredArgsConstructor
public class WebFilterConfig implements WebMvcConfigurer {

    private final CategoryService categoryService;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/sign", "/logout", "/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor2())
                .order(3)
                .addPathPatterns("/", "/sign")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new AddTodoInterceptor(categoryService))
                .order(4)
                .addPathPatterns("/todolist/add", "/todo/revise/**");
    }


//    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
