package gal.udc.fic.nameserver;

import gal.udc.fic.nameserver.cliente.Cliente;
import gal.udc.fic.nameserver.cliente.ClienteGUI;
import gal.udc.fic.nameserver.servidor.CadeaDeResponsabilidade;
import gal.udc.fic.nameserver.servidor.ControlDeAcceso;
import gal.udc.fic.nameserver.servidor.Log;
import gal.udc.fic.nameserver.servidor.ProxyCache;
import gal.udc.fic.nameserver.servidor.Servidor;
import gal.udc.fic.nameserver.servidor.ServidorReal;
import gal.udc.fic.nameserver.usuario.Grupo;
import gal.udc.fic.nameserver.usuario.Individuo;
import gal.udc.fic.nameserver.usuario.Usuario;

public class App {
  public static void main(String[] args) {
    Usuario laura = new Individuo("Laura");
    Grupo mads = new Grupo("mads");
    mads.engadirMembro(laura);

    Servidor imperio =
        new CadeaDeResponsabilidade(new Log(new ProxyCache("Imperiais", "imperio.dat")));
    Servidor rebeldes = new ControlDeAcceso(
        new CadeaDeResponsabilidade(new ServidorReal("Rebeldes", "rebeldes.dat")), mads);
    Cliente c = new ClienteGUI();
    c.engadirServidor(imperio);
    c.engadirServidor(rebeldes);
    c.conectar(laura);
  }
}
