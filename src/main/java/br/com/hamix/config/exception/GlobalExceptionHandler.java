package br.com.hamix.config.exception;

import br.com.hamix.config.exception.custom.ConversionException;
import br.com.hamix.config.exception.custom.DataNotFoundedException;
import br.com.hamix.config.exception.custom.DatabaseException;
import br.com.hamix.config.exception.custom.StorageException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@RestControllerAdvice
@Hidden
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<String> handleDatabaseException(DatabaseException ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro conexão com o banco ");
    }

    @ExceptionHandler(ConversionException.class)
    public ResponseEntity<String> handleConversionException(ConversionException ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Ocorreu um erro na conversão de um campo da request");
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<String> handleStorageException(StorageException ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro no serviço de armazenagem");
    }

    @ExceptionHandler(DataNotFoundedException.class)
    public ResponseEntity<String> handleDataNotFoundedException(ConversionException ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não foi possível encontrar a entidade com os dados passados");
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<String> handleDataNotFoundedException(InternalAuthenticationServiceException ex) {
        log.error("Usuário não encontrado", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Não foi possível encontrar o usuário");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleDataNotFoundedException(HttpMessageNotReadableException ex) {
        log.error("Requisição mal formatada", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Não foi possível fazer a leitura dos dados passados ");
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleDataNotFoundedException(MaxUploadSizeExceededException ex) {
        log.error("Imagem ultrapassou o limite de tamanho", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("A imagem ultrapassou o limite de tamanho ");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro inesperado, tente novamente mais tarde");
    }
}
