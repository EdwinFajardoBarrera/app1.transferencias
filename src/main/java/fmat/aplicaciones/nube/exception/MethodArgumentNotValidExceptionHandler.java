package fmat.aplicaciones.nube.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        
        List<String> errorList = new ArrayList<>();
        result.getFieldErrors().forEach((fieldError) -> {
        	errorList.add(fieldError.getField()+" : " +fieldError.getDefaultMessage());
        });
        
        return new Error(HttpStatus.BAD_REQUEST, errorList);
    }

    public static class Error{
    	private int errorCode;
    	private String error;
    	private List<String> fieldErrors = new ArrayList<>();
    	
    	public Error(HttpStatus status, List<String> fieldErrors ) {
    		this.errorCode = status.value();
    		this.error = status.name();
    		this.fieldErrors = fieldErrors;
    	}

		public int getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

		public List<String> getFieldErrors() {
			return fieldErrors;
		}

		public void setFieldErrors(List<String> fieldErrors) {
			this.fieldErrors = fieldErrors;
		}
    }
}
