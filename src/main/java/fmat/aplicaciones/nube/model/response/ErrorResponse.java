package fmat.aplicaciones.nube.model.response;

public class ErrorResponse {

    private int code;
    private String message;
  
    public ErrorResponse() {
    }
  
    public String getMessage() {
      return this.message;
    }
  
    public void setMessage(String message) {
      this.message = message;
    }
  
    public int getCode() {
      return this.code;
    }
  
    public void setCode(int code) {
      this.code = code;
    }
  
  }