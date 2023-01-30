package gal.udc.fic.nameserver.usuario;

import java.util.ArrayList;

public class Grupo extends UsuarioAbs {

  public Grupo(String nome) {
    super(nome);
    membros = new ArrayList<Usuario>();
  }

  public boolean autorizar(Usuario outro) {
    for (Usuario u : membros) {
      if (u.autorizar(outro)) {
        return true;
      }
    }
    return false;
  }

  public void engadirMembro(Usuario usuario) {
    membros.add(usuario);
  }

  public void eliminarMembro(Usuario usuario) {
    membros.remove(usuario);
  }

  private ArrayList<Usuario> membros;
}
