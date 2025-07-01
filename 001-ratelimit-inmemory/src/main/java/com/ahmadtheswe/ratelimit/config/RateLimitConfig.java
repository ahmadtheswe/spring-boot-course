package com.ahmadtheswe.ratelimit.config;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.NonNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class RateLimitConfig extends OncePerRequestFilter {

  private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

  @Bean
  public FilterRegistrationBean<RateLimitConfig> rateLimitFilter() {
    FilterRegistrationBean<RateLimitConfig> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(this);
    registrationBean.addUrlPatterns("/hello/limited");
    registrationBean.setOrder(1);
    return registrationBean;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    String ip = getIP(request);
    Bucket bucket = buckets.computeIfAbsent(ip, this::newBucket);

    if (bucket.tryConsume(1)) {
      filterChain.doFilter(request, response);
    } else {
      response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
      response.getWriter().write("Too many requests");
    }
  }

  private Bucket newBucket(String key) {
    return Bucket.builder()
        .addLimit(limit -> limit.capacity(5).refillGreedy(5, Duration.ofMinutes(1)))
        .build();
  }

  private String getIP(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.isEmpty()) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}
