package gal.udc.fic.nameserver.servidor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.jupiter.api.Assertions.*;
import gal.udc.fic.nameserver.usuario.Grupo;
import gal.udc.fic.nameserver.usuario.Individuo;

/**
 * Conxunto de tests que proban a entidade CadeaDeResponsabilidade e as súas funcionalidades: -
 * testCreacionCadeaDeResponsabilidade: Proba que se poidan crear CadeasDeResponsabilidade con
 * diferentes ... ... parámetros de entrada. - testStringCadeaDeResponsabilidade: Comproba o
 * funcionamento dos métodos "obterNome()" e "toString()" da ... ... entidade
 * CadeaDeResponsabilidade. - testMestresCadeaDeResponsabilidade: Proba a implementación dos métodos
 * "establecerMestre()" e "obterMestre()". - testResolverCadeaDeResponsabilidade: Proba a
 * implementación do método "resolver()".
 */
public class CadeaDeResponsabilidadeTest extends TestCase {

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public CadeaDeResponsabilidadeTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(CadeaDeResponsabilidadeTest.class);
  }

  public void testCreacionCadeaDeResponsabilidade() {

    ProxyCache pC = new ProxyCache("Imperiais PC", "imperio.dat");
    ServidorReal sR = new ServidorReal("Rebeldes SR", "rebeldes.dat");
    Log logPC = new Log(pC);

    // Pódense crear CadeasDeResponsabilidade con ProxyCaches, ServidoresReais e Logs como decorado
    CadeaDeResponsabilidade cad0 = new CadeaDeResponsabilidade(pC);
    new CadeaDeResponsabilidade(sR);
    new CadeaDeResponsabilidade(logPC);

    // Pódense crear CadeasDeResponsabilidade con decorados nulos
    new CadeaDeResponsabilidade(null);

    // Pódense crear CadeasDeResponsabilidade con outras CadeasDeResponsabilidade como decorado
    new CadeaDeResponsabilidade(new CadeaDeResponsabilidade(cad0));

  }

  public void testStringCadeaDeResponsabilidade() {

    ServidorReal imperiais = new ServidorReal("Imperiais", "imperio.dat");

    CadeaDeResponsabilidade cadImperiais = new CadeaDeResponsabilidade(imperiais);

    CadeaDeResponsabilidade cadNull = new CadeaDeResponsabilidade(null);

    CadeaDeResponsabilidade cadEmoji =
        new CadeaDeResponsabilidade(new ServidorReal("\uD83D\uDE02", "imperio.dat"));

    CadeaDeResponsabilidade cadSymbols = new CadeaDeResponsabilidade(
        new ServidorReal("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''", "imperio.dat")); // "\" and "
                                                                                        // " " not
                                                                                        // allowed

    assertAll("Comproba os metodos de obtencion de nome",
        // Os métodos "toString()" e "obterNome()" devolven o mesmo: O nome do decorado
        () -> assertEquals(cadImperiais.toString(), cadImperiais.obterNome()),
        () -> assertEquals(cadImperiais.toString(), "Imperiais"),

        // Non se pode invocar aos métodos "obterNome()" e "toString()" dunha
        // CadeaDeResponsabilidade con decorado nulo
        () -> assertThrows(NullPointerException.class, () -> cadNull.obterNome()),
        () -> assertThrows(NullPointerException.class, () -> cadNull.toString()),

        // Os métodos de obtención de nome funcionan correctamente cando o nome do decorado contén
        // emojis ou caracteres especiais
        () -> assertEquals(cadEmoji.obterNome(), "\uD83D\uDE02"),
        () -> assertEquals(cadEmoji.toString(), "\uD83D\uDE02"),

        // Pódense crear Servidores con caracteres especiais no nome
        () -> assertEquals(cadSymbols.obterNome(), "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"),
        () -> assertEquals(cadSymbols.toString(), "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"));

  }

  public void testMestresCadeaDeResponsabilidade() {

    ServidorReal imperiaisReal = new ServidorReal("Imperiais SR", "imperio.dat");
    Servidor imperiais = new ServidorReal("Imperiais", "imperio.dat");

    CadeaDeResponsabilidade imperiaisRealCad = new CadeaDeResponsabilidade(imperiaisReal);
    CadeaDeResponsabilidade imperiaisCad = new CadeaDeResponsabilidade(imperiais);

    // O método "establecerMestre()" sí funciona, establece o mestre da CadeaDeResponsabilidade
    imperiaisRealCad.establecerMestre(imperiaisCad);
    imperiaisCad.establecerMestre(imperiaisRealCad);

    assertAll("Comproba a xestion de mestres (1)",
        // O método "obterMestre()" devolve o mestre da CadeaDeResponsabilidade
        () -> assertEquals(imperiaisRealCad.obterMestre(), imperiaisCad),
        () -> assertEquals(imperiaisCad.obterMestre(), imperiaisRealCad));

    // Pódense establecer nulos como mestre
    imperiaisRealCad.establecerMestre(null);
    imperiaisCad.establecerMestre(null);

    assertAll("Comproba a xestion de mestres (2)",
        // Pódense establecer nulos como mestre
        () -> assertNull(imperiaisRealCad.obterMestre()),
        () -> assertNull(imperiaisCad.obterMestre()));

  }

  public void testResolverCadeaDeResponsabilidade() {

    Individuo u = new Individuo("User");
    Grupo g = new Grupo("Group");
    ProxyCache pcImperiais = new ProxyCache("Imperiais PC", "imperio.dat");
    ServidorReal srRebeldes = new ServidorReal("Rebeldes SR", "rebeldes.dat");

    CadeaDeResponsabilidade cadImperiais = new CadeaDeResponsabilidade(pcImperiais);
    CadeaDeResponsabilidade cadRebeldes = new CadeaDeResponsabilidade(srRebeldes);
    CadeaDeResponsabilidade cadNull = new CadeaDeResponsabilidade(null);

    assertAll("Comproba o metodo resolver 1",
        // O método "resolver()" non imprime pola terminal. O resultado da operación é igual que con
        // ServidorReal e ...
        // ... ProxyCache
        () -> assertNull(cadImperiais.resolver(u, "chave")),

        () -> assertEquals(cadImperiais.resolver(u, "vader"), "Darth Vader"),
        () -> assertNull(cadImperiais.resolver(u, "r2d2")),

        () -> assertEquals(cadRebeldes.resolver(g, "r2d2"), "Arturito"),
        () -> assertNull(cadRebeldes.resolver(u, "vader")));

    // Pódese resolver a chave dun mestre dunha CadeaDeResponsabilidade se o resultado previo é null
    cadImperiais.establecerMestre(cadRebeldes);
    cadRebeldes.establecerMestre(cadImperiais);

    assertAll("Comproba o metodo resolver 2",
        // Pódese resolver a chave dun mestre dunha CadeaDeResponsabilidade se o resultado previo é
        // null
        () -> assertEquals(cadImperiais.resolver(u, "r2d2"), "Arturito"),
        () -> assertEquals(cadRebeldes.resolver(u, "vader"), "Darth Vader"),

        // Non se pode resolver cunha CadeaDeResponsabilidade con decorado nulo
        () -> assertThrows(NullPointerException.class, () -> cadNull.resolver(u, "chave")));

  }

}
