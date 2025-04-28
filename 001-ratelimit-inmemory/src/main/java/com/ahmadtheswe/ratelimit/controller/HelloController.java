package com.ahmadtheswe.ratelimit.controller;

import com.ahmadtheswe.ratelimit.dto.ReturnDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

  @GetMapping("/limited")
  public ReturnDTO<String> limitedApi() {
    return ReturnDTO.<String>builder()
        .message("Hello from limited API")
        .status("success")
        .data("Hello World")
        .build();
  }

  @GetMapping("/unlimited")
  public ReturnDTO<String> unlimitedApi() {
    return ReturnDTO.<String>builder()
        .message("Hello from unlimited API")
        .status("success")
        .data("Hello World")
        .build();
  }
}
