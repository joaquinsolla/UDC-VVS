package gal.udc.fic.nameserver.cliente;

import gal.udc.fic.nameserver.servidor.ProxyCache;
import gal.udc.fic.nameserver.servidor.ServidorReal;
import gal.udc.fic.nameserver.usuario.Grupo;
import gal.udc.fic.nameserver.usuario.Individuo;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Conxunto de tests que proban as entidades Cliente e ClienteGUI e as súas funcionalidades: -
 * testCreacionCliente: Proba que se poidan crear Clientes. - testPreferredSizeCliente: Comproba a
 * xestión das dimensións do ClienteGUI. - testServidoresCliente: Proba a implementación do método
 * "engadirServidor()". - testConectarCliente: Proba a implementación do método "conectar()" con
 * diversos Usuarios.
 */
public class ClienteTest extends TestCase {

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public ClienteTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(ClienteTest.class);
  }

  public void testCreacionCliente() {

    // Pódense crear Clientes e ClientesGUI
    Cliente cliente = new ClienteGUI();
    ClienteGUI clienteGUI = new ClienteGUI();

  }

  public void testPreferredSizeCliente() {

    ClienteGUI clienteGUI = new ClienteGUI();

    clienteGUI.setSize(new Dimension(300, 900));

    assertAll("Comproba as dimensions do ClienteGUI",
        // O método "getPreferredSize()" devolve as dimensións predeterminadas en px do clienteGUI
        () -> assertEquals(clienteGUI.getPreferredSize(), new Dimension(850, 550)),

        // As dimensións do clienteGUI pódense editar e obter cos getters e setter de Size
        () -> assertEquals(clienteGUI.getSize(), new Dimension(300, 900)));

  }

  public void testServidoresCliente() {

    ServidorReal imperiais = new ServidorReal("Imperiais", "imperio.dat");
    ProxyCache rebeldes = new ProxyCache("Rebeldes", "rebeldes.dat");
    Cliente cliente = new ClienteGUI();
    ClienteGUI clienteGUI = new ClienteGUI();

    // Pódense engadir Servidores aos Clientes
    cliente.engadirServidor(imperiais);
    cliente.engadirServidor(rebeldes);
    clienteGUI.engadirServidor(imperiais);
    clienteGUI.engadirServidor(rebeldes);

    // Pódese engadir varias veces o mesmo Servidor (engádese repetidas veces)
    cliente.engadirServidor(imperiais);
    cliente.engadirServidor(imperiais);
    cliente.engadirServidor(imperiais);

    // Pódense engadir Servidores nulos
    cliente.engadirServidor(null);
    clienteGUI.engadirServidor(null);

  }

  public void testConectarCliente() {

    Individuo u = new Individuo("User");
    Grupo g = new Grupo("Group");
    Cliente cliente = new ClienteGUI();
    ClienteGUI clienteGUI = new ClienteGUI();

    // Pódense conectar tanto Individuos coma Grupos a un Cliente
    cliente.conectar(u);
    clienteGUI.conectar(g);

    // Pódense conectar varios Usuarios a un mesmo Cliente, ademáis pode conectarse o mesmo Usuario
    // varias veces
    cliente.conectar(u);
    cliente.conectar(g);
    clienteGUI.conectar(u);
    clienteGUI.conectar(g);

    // Pódense conectar Usuarios nulos
    cliente.conectar(null);
    clienteGUI.conectar(null);

  }

  // *Non se pode testear a clase ConfigurarDialog porque é privada

}
