package com.ahmadtheswe.ratelimit.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReturnDTO<T> {
  private String message;
  private String status;
  private T data;
}
