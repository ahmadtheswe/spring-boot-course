package com.ahmadtheswe.ratelimit.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
  @Bean
  public FilterRegistrationBean<RateLimitConfig> rateLimitFilter(RateLimitConfig rateLimitConfig) {
    FilterRegistrationBean<RateLimitConfig> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(rateLimitConfig);

    // you can specify the URL patterns to apply the filter
    // for example, to apply the filter to all URLs starting with "/hello"
    // registrationBean.addUrlPatterns("/hello/*");
    // or to apply the filter to a specific URL
    registrationBean.addUrlPatterns("/hello/limited");

    registrationBean.setOrder(1);
    return registrationBean;
  }

}
