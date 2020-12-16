package fmat.aplicaciones.nube.repository;
import fmat.aplicaciones.nube.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query ("SELECT u from Usuario u WHERE u.clave=?1")
    public Usuario findByClave(String usuario);
}