package edu.ut.convocatoria.domain.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionTypes {

    BAD_REQUEST("ERR-001", "Petición Invalida.", HttpStatus.BAD_REQUEST),

    TECHNICAL_ERROR("ERR-002", "Error Interno en la API.", HttpStatus.INTERNAL_SERVER_ERROR),

    BUSINESS_ERROR("ERR-003", "Business Error.", HttpStatus.CONFLICT),

    FORBIDDEN("ERR-004", "Forbidden.", HttpStatus.FORBIDDEN),

    UNAUTHORIZED("ERR-005", "Unauthorized.", HttpStatus.UNAUTHORIZED),

    NOT_FOUND("ERR-007", "Not Found.", HttpStatus.NOT_FOUND);


    private final String code;
    private final String message;
    private final HttpStatus status;

}
