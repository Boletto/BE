package com.demoboletto.exception;

import com.demoboletto.dto.global.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Convertor 에서 바인딩 실패시 발생하는 예외
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException() in GlobalExceptionHandler throw HttpMessageNotReadableException : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.fail(new CommonException(ErrorCode.BAD_REQUEST_JSON)));
    }


    // 지원되지 않는 HTTP 메소드를 사용할 때 발생하는 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleNoPageFoundException(Exception e) {
        log.error("handleNoPageFoundException() in GlobalExceptionHandler throw NoHandlerFoundException : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.fail(new CommonException(ErrorCode.NOT_FOUND_END_POINT)));
    }

    // @Validated 어노테이션을 사용하여 검증을 수행할 때 발생하는 예외
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseDto<?> handleArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}", e.getMessage());
        return ResponseDto.fail(e);
    }

    // 메소드의 인자 타입이 일치하지 않을 때 발생하는 예외
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        log.error("handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentTypeMismatchException : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.fail(e));
    }

    // 필수 파라미터가 누락되었을 때 발생하는 예외
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleArgumentNotValidException(MissingServletRequestParameterException e) {
        log.error("handleArgumentNotValidException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.fail(e));
    }

    // 개발자가 직접 정의한 예외
    @ExceptionHandler(value = {CommonException.class})
    public ResponseEntity<?> handleApiException(CommonException e) {
        log.error("handleApiException() in GlobalExceptionHandler throw CommonException : {}", e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.fail(e));
    }

    // 서버, DB 예외
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("handleException() in GlobalExceptionHandler throw Exception : {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.fail(new CommonException(ErrorCode.INTERNAL_SERVER_ERROR)));
    }
}