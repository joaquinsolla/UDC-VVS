package gal.udc.fic.nameserver.servidor;

import gal.udc.fic.nameserver.usuario.Usuario;

public abstract class Decorador implements Servidor {

  public Decorador(Servidor decorado) {
    this.decorado = decorado;
  }

  public String obterNome() {
    return decorado.obterNome();
  }

  public Servidor obterMestre() {
    return decorado.obterMestre();
  }

  public void establecerMestre(Servidor mestre) {
    decorado.establecerMestre(mestre);
  }

  public String resolver(Usuario usuario, String chave) {
    return decorado.resolver(usuario, chave);
  }

  public String toString() {
    return obterNome();
  }

  private Servidor decorado;

}
