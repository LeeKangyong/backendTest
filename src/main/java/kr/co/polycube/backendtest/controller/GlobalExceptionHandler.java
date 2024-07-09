package kr.co.polycube.backendtest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import kr.co.polycube.backendtest.dto.response.ErrorResponseDto;
import kr.co.polycube.backendtest.exception.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ HttpMessageNotReadableException.class, MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(Exception e, WebRequest request) {
        String errorMessage;
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        } else if (e instanceof HttpMessageNotReadableException) {
            errorMessage = "Invalid request message";
        } else {
            errorMessage = e.getMessage();
        }
        ErrorResponseDto response = new ErrorResponseDto(errorMessage);
        logger.info("Response Status: 400, Reason: " + errorMessage);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        String errorMessage = e.getMessage();
        ErrorResponseDto response = new ErrorResponseDto(errorMessage);
        logger.info("Response Status: 400, Reason: " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
        String errorMessage = "Not Found";
        ErrorResponseDto response = new ErrorResponseDto(errorMessage);
        logger.info("Response Status: 404, Reason: " + errorMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        String errorMessage = e.getMessage();
        ErrorResponseDto response = new ErrorResponseDto("Invalid characters in query string");
        logger.info("Response Status: 400, Reason: " + errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}