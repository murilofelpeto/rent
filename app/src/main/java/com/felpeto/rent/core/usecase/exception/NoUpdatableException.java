package com.felpeto.rent.core.usecase.exception;

public class NoUpdatableException extends RuntimeException {

  public NoUpdatableException(final String message) {
    super(message);
  }
}
