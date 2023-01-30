package gal.udc.fic.nameserver.servidor;

import gal.udc.fic.nameserver.usuario.Usuario;

public class ProxyCache implements Servidor {

  public ProxyCache(String nome, String ficheiro) {
    this.nome = nome;
    this.ficheiro = ficheiro;
    // proxy de creación: atrasa a creación até que o suxeito é necesário
    this.suxeito = null;
    ultimo_valor = null;
    ultima_chave = null;
  }

  private Servidor obterSuxeito() {
    if (this.suxeito == null) {
      this.suxeito = new ServidorReal(nome, ficheiro);
    }
    return this.suxeito;
  }

  public String obterNome() {
    return nome + " (con proxy)";
  }

  public String resolver(Usuario usuario, String chave) {
    if (!chave.equals(ultima_chave)) {
      ultimo_valor = obterSuxeito().resolver(usuario, chave);
      ultima_chave = chave;
    }
    return ultimo_valor;
  }

  public Servidor obterMestre() {
    return null;
  }

  public void establecerMestre(Servidor mestre) {}

  public String toString() {
    return obterNome();
  }

  private String ultimo_valor;
  private String ultima_chave;
  private String ficheiro;
  private String nome;
  private ServidorReal suxeito;

}
