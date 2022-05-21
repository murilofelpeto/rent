package com.felpeto.rent.adapter.input.controller.handler.dto;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class ErrorDto {

  private String message;
  private Integer statusCode;
  private ZonedDateTime timestamp;
  private List<Field> fields;

  public ErrorDto(
      final String message,
      final Integer statusCode,
      final ZonedDateTime timestamp,
      final List<Field> fields) {
    this.message = message;
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.fields = new ArrayList<>(fields);
  }

  public ErrorDto(final String message, final Integer statusCode, final ZonedDateTime timestamp) {
    this.message = message;
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.fields = Collections.emptyList();
  }

  public String getMessage() {
    return message;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  public List<Field> getFields() {
    return new ArrayList<>(fields);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof final ErrorDto errorDto)) {
      return false;
    }

    if (!getMessage().equals(errorDto.getMessage())) {
      return false;
    }
    if (!getStatusCode().equals(errorDto.getStatusCode())) {
      return false;
    }
    if (!getTimestamp().equals(errorDto.getTimestamp())) {
      return false;
    }
    return getFields().equals(errorDto.getFields());
  }

  @Override
  public int hashCode() {
    int result = getMessage().hashCode();
    result = 31 * result + getStatusCode().hashCode();
    result = 31 * result + getTimestamp().hashCode();
    result = 31 * result + getFields().hashCode();
    return result;
  }
}
