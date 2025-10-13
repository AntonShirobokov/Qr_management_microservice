package com.shirobokov.qr_management_microservice.exception;

public class QrCodeAlreadyExistsException extends RuntimeException {
  public QrCodeAlreadyExistsException(String message) {
    super(message);
  }
}
