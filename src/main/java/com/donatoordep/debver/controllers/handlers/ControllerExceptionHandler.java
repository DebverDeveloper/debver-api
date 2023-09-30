package com.donatoordep.debver.controllers.handlers;

import com.donatoordep.debver.dto.FieldMessage;
import com.donatoordep.debver.dto.ValidationError;
import com.donatoordep.debver.services.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(UserExistsInDatabaseException.class)
    public ResponseEntity<CustomizedException> userExistsInDatabase(
            UserExistsInDatabaseException e, HttpServletRequest request) {
        return handlingException(e, HttpStatus.CONFLICT, request.getRequestURI());
    }

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<CustomizedException> notFoundEntityException(
            NotFoundEntityException e, HttpServletRequest request) {
        return handlingException(e, HttpStatus.NOT_FOUND, request.getRequestURI());
    }

    @ExceptionHandler(AnimeAlreadyInCartException.class)
    public ResponseEntity<CustomizedException> animeAlreadyInCart(
            AnimeAlreadyInCartException e, HttpServletRequest request) {
        return handlingException(e, HttpStatus.CONFLICT, request.getRequestURI());
    }

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<CustomizedException> weakPassword(
            WeakPasswordException e, HttpServletRequest request) {
        return handlingException(e, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(IncompatibleEpisodeException.class)
    public ResponseEntity<CustomizedException> incompatibleEpisode(
            IncompatibleEpisodeException e, HttpServletRequest request) {
        return handlingException(e, HttpStatus.UNPROCESSABLE_ENTITY, request.getRequestURI());
    }

    @ExceptionHandler(CodeNotValidException.class)
    public ResponseEntity<CustomizedException> codeNotValid(
            CodeNotValidException e, HttpServletRequest request) {
        return handlingException(e, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<CustomizedException> invalidEnumValue(
            InvalidEnumValueException e, HttpServletRequest request) {
        return handlingException(e, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomizedException> methodArgumentNotValid(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError error = new ValidationError("Invalid data!!",
                HttpStatus.UNPROCESSABLE_ENTITY.value(), request.getRequestURI());
        e.getBindingResult().getFieldErrors()
                .forEach(fieldError ->
                        error.add(new FieldMessage(fieldError.getField(), fieldError.getDefaultMessage())));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    private ResponseEntity<CustomizedException> handlingException(
            Exception e, HttpStatus status, String path) {
        log(e);
        return ResponseEntity.status(status).body(
                new CustomizedException(e.getMessage(), status.value(), path));
    }

private void log(Throwable exception) {
        logger.error("error message {}. Details:", exception.getMessage(), exception);
}

}
