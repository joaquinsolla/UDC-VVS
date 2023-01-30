package gal.udc.fic.nameserver.servidor;

import gal.udc.fic.nameserver.usuario.Usuario;

/**
 * Este compoñente representa a interface do subsistema Servidor.
 *
 * Alén da funcionalidade de recuperar o seu nome (<code>obterNome</code>), un Servidor proporciona
 * un servizo de resolución de nomes (<code>resolver</code>).
 *
 * Para poder xestionar colaboracións de Servidores de xeito transparente, este compoñente ofrece
 * tamén dous métodos para establecer e eliminar un Servidor auxiliar (<code>obterMestre</code> e
 * <code>establecerMestre</code>).
 */

public interface Servidor {

  /**
   * Recupera o nome do servidor.
   *
   * @return nome do servidor
   */
  public String obterNome();

  /**
   * Servizo de resolución de nomes, devolve a cadena de texto asociada a unha chave dada.
   *
   * @param usuario usuario que realiza a consulta
   * @param chave nome a procurar polo servidor
   * @return valor asociado coa clave, <code>null</code> se non se pode resolver
   */
  public String resolver(Usuario usuario, String chave);

  /**
   * Recupera o servidor mestre asociado cun servidor.
   *
   * @return servidor mestre, <code>null</code> se non ten ningún asociado
   */
  public Servidor obterMestre();

  /**
   * Establece o servidor mestre asociado ao servidor.
   *
   * @param mestre servidor que se asociará
   */
  public void establecerMestre(Servidor mestre);

}
