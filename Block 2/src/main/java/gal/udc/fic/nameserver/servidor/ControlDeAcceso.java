package gal.udc.fic.nameserver.servidor;

import gal.udc.fic.nameserver.usuario.Usuario;

public class ControlDeAcceso extends Decorador {

  public ControlDeAcceso(Servidor decorado, Usuario propietario) {
    super(decorado);
    this.propietario = propietario;
  }

  public String obterNome() {
    return super.obterNome() + " (control: " + propietario.obterNome() + ")";
  }

  public String resolver(Usuario usuario, String chave) {
    if (propietario.autorizar(usuario)) {
      return super.resolver(usuario, chave);
    } else {
      return null;
    }
  }

  private Usuario propietario;

}
