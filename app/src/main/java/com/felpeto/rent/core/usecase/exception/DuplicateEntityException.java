package com.felpeto.rent.core.usecase.exception;

public class DuplicateEntityException extends RuntimeException {

  public DuplicateEntityException(final String message) {
    super(message);
  }
}
