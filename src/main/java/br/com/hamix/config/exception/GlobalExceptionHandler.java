package br.com.hamix.config.exception;

import br.com.hamix.config.exception.custom.ConversionException;
import br.com.hamix.config.exception.custom.DataNotFoundedException;
import br.com.hamix.config.exception.custom.DatabaseException;
import br.com.hamix.config.exception.custom.StorageException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Hidden
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro no banco: " + ex.getMessage());
    }

    @ExceptionHandler(ConversionException.class)
    public ResponseEntity<String> handleConversionException(ConversionException ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro na conversão: " + ex.getMessage());
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<String> handleStorageException(StorageException ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro no serviço de Storage: " + ex.getMessage());
    }

    @ExceptionHandler(DataNotFoundedException.class)
    public ResponseEntity<String> handleDataNotFoundedException(ConversionException ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não foi possível encontrar a entidade com os dados passados: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro inesperado, tente novamente mais tarde\n Erro:{" + ex.getMessage()+"}");
    }



}
