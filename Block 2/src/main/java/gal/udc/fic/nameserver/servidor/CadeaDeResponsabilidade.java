package gal.udc.fic.nameserver.servidor;

import gal.udc.fic.nameserver.usuario.Usuario;

public class CadeaDeResponsabilidade extends Decorador {

  public CadeaDeResponsabilidade(Servidor decorado) {
    super(decorado);
    mestre = null;
  }

  public String resolver(Usuario usuario, String chave) {
    String resultado = super.resolver(usuario, chave);
    if ((resultado == null) && (mestre != null)) {
      resultado = mestre.resolver(usuario, chave);
    }
    return resultado;
  }

  public Servidor obterMestre() {
    return mestre;
  }

  public void establecerMestre(Servidor mestre) {
    this.mestre = mestre;
  }

  private Servidor mestre;
}
