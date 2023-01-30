package gal.udc.fic.nameserver.usuario;

/**
 * Este compoñente representa a interface do subsistema Usuario.
 *
 * Un Usuario ofrece a funcionalidade de recuperar o seu nome (<code>obterNome</code>), un
 * identificador (nº entero) que o identifica de xeito único (<code>obtenerID</code>), e un método
 * que comproba a autorizar a outro Usuario (<code>autorizar</code>).
 *
 * Para poder xestionar agrupacións de Usuario de xeito transparente, este compoñente ofrece tamén
 * dous métodos para engadir e eliminar Usuarios (<code>engadirMembro</code> e
 * <code>eliminarMembro</code>).
 */

public interface Usuario {

  /**
   * Recupera o nome do usuario.
   *
   * @return nome do usuario
   */
  public String obterNome();

  /**
   * Recupera o identificador do usuario.
   *
   * @return identificador do usuario
   */
  public int obterID();

  /**
   * Indica se o usuario autoriza a outro usuario.
   *
   * @param outro usuario a ser autorizado
   * @return true se o usuario autoriza ao outro usuario
   */
  public boolean autorizar(Usuario outro);

  /**
   * Incorpora un usuario.
   *
   * @param usuario usuario a ser incorporado
   */
  public void engadirMembro(Usuario usuario);

  /**
   * Elimina un usuario.
   *
   * @param usuario usuario a ser eliminado
   */
  public void eliminarMembro(Usuario usuario);

}
