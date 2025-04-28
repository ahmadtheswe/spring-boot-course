package com.ahmadtheswe.ratelimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RatelimitInmemoryApplication {

  public static void main(String[] args) {
    SpringApplication.run(RatelimitInmemoryApplication.class, args);
  }

}
