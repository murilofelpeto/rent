package com.felpeto.rent.core.usecase.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(final String message) {
    super(message);
  }
}
