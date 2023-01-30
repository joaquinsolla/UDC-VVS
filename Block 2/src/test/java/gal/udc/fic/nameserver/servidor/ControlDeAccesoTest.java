package gal.udc.fic.nameserver.servidor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.jupiter.api.Assertions.*;
import gal.udc.fic.nameserver.usuario.Grupo;
import gal.udc.fic.nameserver.usuario.Individuo;

/**
 * Conxunto de tests que proban a entidade ControlDeAcceso e as súas funcionalidades: -
 * testCreacionControlDeAcceso: Proba que se poidan crear ControisDeAcceso con diferentes parámetros
 * de entrada. - testStringControlDeAcceso: Comproba o funcionamento dos métodos "obterNome()" e
 * "toString()" da entidade ... ... ControlDeAcceso. - testMestresControlDeAcceso: Proba a
 * implementación dos métodos "establecerMestre()" e "obterMestre()". - testResolverControlDeAcceso:
 * Proba a implementación do método "resolver()".
 */
public class ControlDeAccesoTest extends TestCase {

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public ControlDeAccesoTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(ControlDeAccesoTest.class);
  }

  java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();

  public void testCreacionControlDeAcceso() {

    Individuo u = new Individuo("User");
    Grupo g = new Grupo("Group");
    ProxyCache pC = new ProxyCache("Imperiais PC", "imperio.dat");
    ServidorReal sR = new ServidorReal("Rebeldes SR", "rebeldes.dat");
    Log logPC = new Log(pC);
    CadeaDeResponsabilidade cad = new CadeaDeResponsabilidade(sR);

    // Pódense crear ControisDeAcceso con ProxyCaches, ServidoresReais, Logs e
    // CadeasDeResponsabilidade como ...
    // ... decorado e con Individuos e Grupos como usuario
    ControlDeAcceso ctrl = new ControlDeAcceso(pC, u);
    new ControlDeAcceso(sR, u);
    new ControlDeAcceso(logPC, g);
    new ControlDeAcceso(cad, g);

    // Pódense crear ControisDeAcceso con decorados e usuarios nulos
    new ControlDeAcceso(null, null);

    // Pódense crear ControisDeAcceso con outros ControisDeAcceso como decorado
    new ControlDeAcceso(new ControlDeAcceso(ctrl, u), u);

  }

  public void testStringControlDeAcceso() {

    Individuo u = new Individuo("User");

    ServidorReal imperiais = new ServidorReal("Imperiais", "imperio.dat");

    ControlDeAcceso ctrlImperiais = new ControlDeAcceso(imperiais, u);

    ControlDeAcceso ctrlDecNull = new ControlDeAcceso(null, u);

    ControlDeAcceso ctrlUsrNull = new ControlDeAcceso(imperiais, null);

    ControlDeAcceso ctrlEmoji =
        new ControlDeAcceso(new ServidorReal("\uD83D\uDE02", "imperio.dat"), u);

    ControlDeAcceso ctrlSymbols = new ControlDeAcceso(
        new ServidorReal("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''", "imperio.dat"), u); // "\"
                                                                                           // and "
                                                                                           // " "
                                                                                           // not
                                                                                           // allowed

    assertAll("Comproba os metodos de obtencion de nome",
        // Os métodos "toString()" e "obterNome()" devolven o mesmo: O nome do decorado + "( control
        // : " + o usuario + ")"
        () -> assertEquals(ctrlImperiais.toString(), ctrlImperiais.obterNome()),
        () -> assertEquals(ctrlImperiais.toString(), "Imperiais (control: User)"),

        // Non se pode invocar aos métodos "obterNome()" e "toString()" dun ControlDeAcceso con
        // decorado nulo
        () -> assertThrows(NullPointerException.class, () -> ctrlDecNull.obterNome()),
        () -> assertThrows(NullPointerException.class, () -> ctrlDecNull.toString()),

        // Non se pode invocar aos métodos "obterNome()" e "toString()" dun ControlDeAcceso con
        // usuario nulo
        () -> assertThrows(NullPointerException.class, () -> ctrlUsrNull.obterNome()),
        () -> assertThrows(NullPointerException.class, () -> ctrlUsrNull.toString()),

        // Os métodos de obtención de nome funcionan correctamente cando o nome do decorado contén
        // emojis ou caracteres especiais
        () -> assertEquals(ctrlEmoji.obterNome(), "\uD83D\uDE02 (control: User)"),
        () -> assertEquals(ctrlEmoji.toString(), "\uD83D\uDE02 (control: User)"),

        // Pódense crear Servidores con caracteres especiais no nome
        () -> assertEquals(ctrlSymbols.obterNome(),
            "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  '' (control: User)"),
        () -> assertEquals(ctrlSymbols.toString(),
            "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  '' (control: User)"));

  }

  public void testMestresControlDeAcceso() {

    Individuo u = new Individuo("User");
    Grupo g = new Grupo("Group");
    ServidorReal imperiaisReal = new ServidorReal("Imperiais SR", "imperio.dat");
    Servidor imperiais = new ServidorReal("Imperiais", "imperio.dat");
    CadeaDeResponsabilidade imperiaisCad = new CadeaDeResponsabilidade(imperiais);

    ControlDeAcceso imperiaisRealCtrl = new ControlDeAcceso(imperiaisReal, u);
    ControlDeAcceso imperiaisCtrl = new ControlDeAcceso(imperiais, g);
    ControlDeAcceso imperiaisCadCtrl = new ControlDeAcceso(imperiaisCad, u);
    ControlDeAcceso nullCtrl = new ControlDeAcceso(null, null);

    // O método "establecerMestre()" delega no mesmo método do decorado
    imperiaisRealCtrl.establecerMestre(imperiaisCtrl);
    imperiaisCtrl.establecerMestre(imperiaisRealCtrl);
    imperiaisCad.establecerMestre(imperiaisCtrl);

    assertAll("Comproba a xestion de mestres (1)",
        // O método "obterMestre()" tamén delega no mesmo método do decorado
        () -> assertNull(imperiaisRealCtrl.obterMestre()),
        () -> assertNull(imperiaisCtrl.obterMestre()),
        () -> assertEquals(imperiaisCadCtrl.obterMestre(), imperiaisCtrl)

    );

    // Pódense establecer nulos como mestre
    imperiaisRealCtrl.establecerMestre(null);
    imperiaisCtrl.establecerMestre(null);
    imperiaisCadCtrl.establecerMestre(null);

    assertAll("Comproba a xestion de mestres (2)",
        // Pódense establecer nulos como mestre
        () -> assertNull(imperiaisRealCtrl.obterMestre()),
        () -> assertNull(imperiaisCtrl.obterMestre()),
        () -> assertNull(imperiaisCadCtrl.obterMestre()), // O único que realmente devolve o seu
                                                          // mestre

        // Non se pode establecer/obter o metre dun ControlDeAcceso con decorado e/ou usuario nulos
        () -> assertThrows(NullPointerException.class, nullCtrl::obterMestre),
        () -> assertThrows(NullPointerException.class, () -> nullCtrl.establecerMestre(imperiais)),
        () -> assertThrows(NullPointerException.class, nullCtrl::obterMestre));

  }

  public void testResolverControlDeAcceso() {

    Individuo u = new Individuo("User");
    Individuo exU = new Individuo("External User");
    ProxyCache pcImperiais = new ProxyCache("Imperiais PC", "imperio.dat");
    ServidorReal srRebeldes = new ServidorReal("Rebeldes SR", "rebeldes.dat");
    Log logRebeldes = new Log(srRebeldes);
    CadeaDeResponsabilidade cadImperiais = new CadeaDeResponsabilidade(pcImperiais);

    ControlDeAcceso imperiaisPcCtrl = new ControlDeAcceso(pcImperiais, u);
    ControlDeAcceso rebeldesSrCtrl = new ControlDeAcceso(srRebeldes, u);
    ControlDeAcceso rebeldesSrLogCtrl = new ControlDeAcceso(logRebeldes, u);
    ControlDeAcceso imperiaisPcCadCtrl = new ControlDeAcceso(cadImperiais, u);
    ControlDeAcceso nullDecCtrl = new ControlDeAcceso(null, u);
    ControlDeAcceso nullUsrCtrl = new ControlDeAcceso(pcImperiais, null);

    assertAll("Comproba o metodo resolver (1)",
        // Se o usuario do ControlDeAcceso autoriza ao usuario que solicita resolver, delégase no
        // método do decorado ...
        // ... senon devolve null
        () -> assertNull(imperiaisPcCtrl.resolver(u, "chave")),
        () -> assertNull(imperiaisPcCtrl.resolver(u, "luke")),
        () -> assertEquals(imperiaisPcCtrl.resolver(u, "vader"), "Darth Vader"),
        () -> assertNull(imperiaisPcCtrl.resolver(exU, "vader")),

        () -> assertNull(rebeldesSrCtrl.resolver(u, "chave")),
        () -> assertNull(rebeldesSrCtrl.resolver(u, "vader")),
        () -> assertEquals(rebeldesSrCtrl.resolver(u, "luke"), "Luke Skywalker"),
        () -> assertNull(rebeldesSrCtrl.resolver(exU, "luke")));

    System.setOut(new java.io.PrintStream(out));
    assertAll("Comproba o metodo resolver (2)",
        () -> assertNull(rebeldesSrLogCtrl.resolver(u, "chave")),
        () -> assertEquals(out.toString().trim(),
            "[Rebeldes SR] Resolver: Usuario=User, chave=chave\r\n" + "         Resultado=null"));
    out.reset();

    assertAll("Comproba o metodo resolver (3)",
        () -> assertNull(rebeldesSrLogCtrl.resolver(u, "vader")),
        () -> assertEquals(out.toString().trim(),
            "[Rebeldes SR] Resolver: Usuario=User, chave=vader\r\n" + "         Resultado=null"));
    out.reset();

    assertAll("Comproba o metodo resolver (4)",
        () -> assertEquals(rebeldesSrLogCtrl.resolver(u, "luke"), "Luke Skywalker"),
        () -> assertEquals(out.toString().trim(),
            "[Rebeldes SR] Resolver: Usuario=User, chave=luke\r\n"
                + "         Resultado=Luke Skywalker"));
    out.reset();

    assertAll("Comproba o metodo resolver (5)",
        () -> assertNull(rebeldesSrLogCtrl.resolver(exU, "luke")),

        () -> assertNull(imperiaisPcCadCtrl.resolver(u, "chave")),
        () -> assertNull(imperiaisPcCadCtrl.resolver(u, "luke")),
        () -> assertEquals(imperiaisPcCadCtrl.resolver(u, "vader"), "Darth Vader"),
        () -> assertNull(imperiaisPcCadCtrl.resolver(exU, "vader")),

        // Non se pode resolver cun ControlDeAcceso con decorado e/ou usuario nulo
        () -> assertThrows(NullPointerException.class, () -> nullDecCtrl.resolver(u, "vader")),
        () -> assertThrows(NullPointerException.class, () -> nullUsrCtrl.resolver(u, "vader")),
        () -> assertThrows(NullPointerException.class, () -> nullDecCtrl.resolver(null, "vader")),
        () -> assertThrows(NullPointerException.class, () -> nullUsrCtrl.resolver(null, "vader")));

  }

}
