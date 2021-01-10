package fmat.aplicaciones.nube.model;

public class LoginObject {
    private Usuario usuario;
    private String token;

  public LoginObject() {
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
    
}
