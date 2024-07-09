package kr.co.polycube.backendtest.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.polycube.backendtest.filter.SpecialCharacterFilter;

@Configuration
public class FilterConfig {

	@Bean
    public FilterRegistrationBean<SpecialCharacterFilter> specialCharacterFilterBean() {
        FilterRegistrationBean<SpecialCharacterFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SpecialCharacterFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);  // 필터 순서 설정
        return registrationBean;
    }
}
