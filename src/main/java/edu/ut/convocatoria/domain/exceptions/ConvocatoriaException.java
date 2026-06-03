package edu.ut.convocatoria.domain.exceptions;

import edu.ut.convocatoria.domain.enumerated.ExceptionTypes;
import lombok.Getter;

@Getter
public class ConvocatoriaException extends RuntimeException {
    private final ExceptionTypes exceptionType;

    public ConvocatoriaException(String message, ExceptionTypes exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public ConvocatoriaException(String message, Throwable cause, ExceptionTypes exceptionType) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }
}
