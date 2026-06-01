package edu.ut.convocatoria.domain.exceptions;

import edu.ut.convocatoria.domain.enumerated.ExceptionTypes;
import lombok.Getter;

@Getter
public class ConvocatoriaExceptions extends RuntimeException {
    private final ExceptionTypes exceptionType;

    public ConvocatoriaExceptions(String message, ExceptionTypes exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

    public ConvocatoriaExceptions(String message, Throwable cause, ExceptionTypes exceptionType) {
        super(message, cause);
        this.exceptionType = exceptionType;
    }
}
