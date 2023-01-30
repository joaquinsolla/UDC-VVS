package gal.udc.fic.nameserver.usuario;

public abstract class UsuarioAbs implements Usuario {

  public UsuarioAbs(String nome) {
    this.nome = nome;
    id = obterSeguinteID();
  }

  public String obterNome() {
    return nome;
  }

  public int obterID() {
    return id;
  }

  public void engadirMembro(Usuario usuario) {}

  public void eliminarMembro(Usuario usuario) {}

  public String toString() {
    return obterNome();
  }

  private int obterSeguinteID() {
    return ultimoID++;
  }

  private String nome;
  private int id;
  private static int ultimoID = 0;

}
