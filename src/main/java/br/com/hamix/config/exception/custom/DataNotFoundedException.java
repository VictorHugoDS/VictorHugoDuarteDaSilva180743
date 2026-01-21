package br.com.hamix.config.exception.custom;

public class DataNotFoundedException extends RuntimeException {
  public DataNotFoundedException(String message) {
    super(message);
  }
}
