package cepredis.infra;

import cepredis.dtos.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(Exception exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity connectionException(IOException exception){
        ExceptionDTO exceptionDTO = new ExceptionDTO("Erro ao conectar com serviço do ViaCEP", "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }

}
