package gal.udc.fic.nameserver.servidor;

import gal.udc.fic.nameserver.usuario.Usuario;

public class Log extends Decorador {

  public Log(Servidor decorado) {
    super(decorado);
  }

  public String obterNome() {
    return super.obterNome() + " (Log)";
  }

  public String resolver(Usuario usuario, String chave) {
    System.out.println("[" + super.obterNome() + "] Resolver: Usuario=" + usuario.obterNome()
        + ", chave=" + chave);
    String resultado = super.resolver(usuario, chave);
    System.out.println("         Resultado=" + resultado);
    return resultado;
  }

}
