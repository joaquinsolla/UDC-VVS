package gal.udc.fic.nameserver.usuario;

public class Individuo extends UsuarioAbs {

  public Individuo(String nome) {
    super(nome);
  }

  public boolean autorizar(Usuario outro) {
    return (outro.obterID() == obterID());
  }

}
