package gal.udc.fic.nameserver.cliente;

import gal.udc.fic.nameserver.servidor.Servidor;
import gal.udc.fic.nameserver.usuario.Usuario;

/**
 * Este compoñente representa un Cliente dos subsistemas Servidor e Usuario.
 */

public interface Cliente {

  /**
   * Incorpora un Servidor aos servidores coñecidos polo cliente.
   *
   * @param servidor servidor a incorporar
   */
  public void engadirServidor(Servidor servidor);

  /**
   * Conecta un usuario ao cliente para poder realizar peticións aos servidores coñecidos.
   *
   * @param usuario usuario a empregar nas peticións
   */
  public void conectar(Usuario usuario);
}
