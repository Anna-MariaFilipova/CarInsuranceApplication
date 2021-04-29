package company.carsProject.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import company.carsProject.model.Message;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {

		String errorMessageDescription = ex.getLocalizedMessage();

		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}
		Message errorMessage = new Message(new Date(), ex.getLocalizedMessage());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { DublicateException.class })
	public ResponseEntity<Object> handleDublicateException(Exception ex, WebRequest request) {

		String errorMessageDescription = ex.getLocalizedMessage();

		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}
		Message errorMessage = new Message(new Date(), ex.getLocalizedMessage());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(value = {NotFoundException.class })
	public ResponseEntity<Object> handleNotFoundExceptionException(Exception ex, WebRequest request) {

		String errorMessageDescription = ex.getLocalizedMessage();

		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}
		Message errorMessage = new Message(new Date(), ex.getLocalizedMessage());

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

}
