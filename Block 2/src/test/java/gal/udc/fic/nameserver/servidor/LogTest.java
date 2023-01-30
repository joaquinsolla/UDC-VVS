package gal.udc.fic.nameserver.servidor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.jupiter.api.Assertions.*;
import gal.udc.fic.nameserver.usuario.Grupo;
import gal.udc.fic.nameserver.usuario.Individuo;

/**
 * Conxunto de tests que proban a entidade Log e as súas funcionalidades: - testCreacionLog: Proba
 * que se poidan crear Logs con diferentes parámetros de entrada. - testStringLog: Comproba o
 * funcionamento dos métodos "obterNome()" e "toString()" da entidade Log. - testMestresLog: Proba a
 * implementación dos métodos "establecerMestre()" e "obterMestre()". - testResolverLog: Proba a
 * implementación do método "resolver()".
 */
public class LogTest extends TestCase {

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public LogTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(LogTest.class);
  }

  java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();

  public void testCreacionLog() {

    ProxyCache pC = new ProxyCache("Imperiais PC", "imperio.dat");
    ServidorReal sR = new ServidorReal("Rebeldes SR", "rebeldes.dat");

    // Pódense crear Logs con ProxyCaches e ServidoresReais como decorado
    Log l0 = new Log(pC);
    new Log(sR);

    // Pódense crear Logs con decorados nulos
    new Log(null);

    // Pódense crear Logs con outros Logs como decorado
    new Log(new Log(l0));

  }

  public void testStringLog() {

    ServidorReal imperiais = new ServidorReal("Imperiais", "imperio.dat");

    Log imperiaisLog = new Log(imperiais);

    Log lNull = new Log(null);

    Log lEmoji = new Log(new ServidorReal("\uD83D\uDE02", "imperio.dat"));

    Log lSymbols =
        new Log(new ServidorReal("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''", "imperio.dat")); // "\"
                                                                                                // and
                                                                                                // "
                                                                                                // "
                                                                                                // "
                                                                                                // not
                                                                                                // allowed

    assertAll("Comproba os metodos de obtencion de nome",
        // Os métodos "toString()" e "obterNome()" devolven o mesmo: O nome do decorado + " (Log)"
        () -> assertEquals(imperiaisLog.toString(), imperiaisLog.obterNome()),
        () -> assertEquals(imperiaisLog.toString(), "Imperiais (Log)"),

        // Non se pode invocar aos métodos "obterNome()" e "toString()" dun Log con decorado nulo
        () -> assertThrows(NullPointerException.class, () -> lNull.obterNome()),
        () -> assertThrows(NullPointerException.class, () -> lNull.toString()),

        // Os métodos de obtención de nome funcionan correctamente cando o nome do decorado contén
        // emojis ou caracteres especiais
        () -> assertEquals(lEmoji.obterNome(), "\uD83D\uDE02 (Log)"),
        () -> assertEquals(lEmoji.toString(), "\uD83D\uDE02 (Log)"),

        // Pódense crear Servidores con caracteres especiais no nome
        () -> assertEquals(lSymbols.obterNome(),
            "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  '' (Log)"),
        () -> assertEquals(lSymbols.toString(),
            "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  '' (Log)"));

  }

  public void testMestresLog() {

    ServidorReal imperiaisReal = new ServidorReal("Imperiais SR", "imperio.dat");
    Servidor imperiais = new ServidorReal("Imperiais", "imperio.dat");

    Log imperiaisRealLog = new Log(imperiaisReal);
    Log imperiaisLog = new Log(imperiais);

    imperiaisRealLog.establecerMestre(imperiaisLog);
    imperiaisLog.establecerMestre(imperiaisRealLog);
    imperiaisRealLog.establecerMestre(imperiaisReal);
    imperiaisLog.establecerMestre(imperiais);

    // Pódense establecer nulos como mestre
    imperiaisRealLog.establecerMestre(null);
    imperiaisLog.establecerMestre(null);

    assertAll("Comproba a xestion de mestres",
        // O método "obterMestre()" sempre devolve null (delega en "decorado.estalecerMestre()")
        () -> assertNull(imperiaisRealLog.obterMestre()),
        () -> assertNull(imperiaisLog.obterMestre()));

  }

  public void testResolverLog() {

    Individuo u = new Individuo("User");
    Grupo g = new Grupo("Group");
    ProxyCache pC = new ProxyCache("Imperiais PC", "imperio.dat");
    ServidorReal sR = new ServidorReal("Rebeldes SR", "rebeldes.dat");

    Log logPC = new Log(pC);
    Log logSR = new Log(sR);
    Log lNull = new Log(null);

    System.setOut(new java.io.PrintStream(out));

    assertAll("Comproba o metodo resolver (1)",
        // O método "resolver()" imprime pola terminal a operación solicitada e o seu resultado. Os
        // resultados son ...
        // ... iguais que con ServidorReal e ProxyCache
        () -> assertNull(logPC.resolver(u, "chave")),
        () -> assertEquals(out.toString().trim(),
            "[Imperiais PC (con proxy)] Resolver: Usuario=User, chave=chave"
                + "\r\n         Resultado=null"));
    out.reset();

    assertAll("Comproba o metodo resolver (2)",
        // O método "resolver()" imprime pola terminal a operación solicitada e o seu resultado. Os
        // resultados son ...
        // ... iguais que con ServidorReal e ProxyCache
        () -> assertEquals(logPC.resolver(u, "vader"), "Darth Vader"),
        () -> assertEquals(out.toString().trim(),
            "[Imperiais PC (con proxy)] Resolver: Usuario=User, chave=vader"
                + "\r\n         Resultado=Darth Vader"));
    out.reset();

    assertAll("Comproba o metodo resolver (3)",
        // O método "resolver()" imprime pola terminal a operación solicitada e o seu resultado. Os
        // resultados son ...
        // ... iguais que con ServidorReal e ProxyCache
        () -> assertEquals(logSR.resolver(g, "r2d2"), "Arturito"),
        () -> assertEquals(out.toString().trim(),
            "[Rebeldes SR] Resolver: Usuario=Group, chave=r2d2"
                + "\r\n         Resultado=Arturito"));
    out.reset();

    assertAll("Comproba o metodo resolver (4)",
        // Non se pode resolver cun Log con decorado nulo
        () -> assertThrows(NullPointerException.class, () -> lNull.resolver(u, "chave")));


  }

}
