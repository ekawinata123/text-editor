package org.example.exception;

import org.example.enumeration.ErrorMessageConstant;

public class IllegalParameterException extends Exception{

  public IllegalParameterException(String... parameters) {
    super(String.format(ErrorMessageConstant.INVALID_PARAMETER_INPUT, String.join(", ", parameters)));
  }

}
