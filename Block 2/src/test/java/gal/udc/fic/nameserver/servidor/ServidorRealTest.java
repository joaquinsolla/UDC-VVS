package gal.udc.fic.nameserver.servidor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.jupiter.api.Assertions.*;
import gal.udc.fic.nameserver.usuario.Individuo;

/**
 * Conxunto de tests que proban a entidade ServidorReal e as súas funcionalidades: -
 * testCreacionServidorReal: Proba que se poidan crear ServidoresReais con diferentes parámetros de
 * entrada. - testStringServidorReal: Comproba o funcionamento dos métodos "obterNome()" e
 * "toString()" da entidade ServidorReal. - testMestresServidorReal: Proba a implementación dos
 * métodos "establecerMestre()" e "obterMestre()". - testResolverServidorReal: Proba a
 * implementación do método "resolver()".
 */
public class ServidorRealTest extends TestCase {

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public ServidorRealTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(ServidorRealTest.class);
  }

  public void testCreacionServidorReal() {

    // Pódense crear ServidoresReais e Servidores
    ServidorReal imperiais = new ServidorReal("Imperiais", "imperio.dat");
    Servidor rebeldes = new ServidorReal("Rebeldes", "rebeldes.dat");

    // Pódense crear ServidoresReais co mesmo nome
    ServidorReal clon1 = new ServidorReal("Clon", "imperio.dat");
    ServidorReal clon2 = new ServidorReal("Clon", "imperio.dat");

    // Pódense crear ServidoresReais sen nome
    new ServidorReal(null, "imperio.dat");

    // Pódense crear ServidoresReais con nomes moi longos
    ServidorReal sLong = new ServidorReal(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eu imperdiet enim. Aenean gravida mattis tristique. Morbi luctus eros a mi maximus, id ornare mauris viverra. Integer blandit vehicula congue. Duis purus odio, scelerisque at convallis iaculis, dapibus eget dolor. Sed semper vestibulum lorem sed congue. Praesent egestas laoreet iaculis. Sed at ante ut enim rhoncus pulvinar."
            + "Vestibulum rhoncus sed nunc non rutrum. Praesent ac nunc sem. Aenean ac odio malesuada, faucibus nisl sit blandit.",
        "imperio.dat");
    ServidorReal sLonger = new ServidorReal(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas varius erat diam, eget laoreet lorem semper sit amet. Donec a lobortis nulla. Quisque nec felis in turpis facilisis imperdiet. Maecenas ante nisi, sollicitudin ac tellus vitae, pretium malesuada enim. Aliquam laoreet luctus blandit. Proin iaculis odio mauris, at ultricies lacus dictum sit amet. In sit amet suscipit nisi. Vivamus pretium purus mi, eu pulvinar lacus venenatis in. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean laoreet quam non lacus ornare, sed rutrum metus vulputate. Aliquam erat volutpat. Ut nec velit vitae nisi tincidunt maximus."
            + "Duis tincidunt lorem at diam sagittis egestas. Integer sed ultrices risus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras quis sapien eu neque accumsan feugiat eu nec leo. Sed volutpat tincidunt aliquet. Donec eget ullamcorper metus. In fermentum venenatis vulputate. Suspendisse tincidunt.",
        "imperio.dat");

    // Pódense crear ServidoresReais con emojis no nome
    new ServidorReal("\uD83D\uDE02", "imperio.dat");

    // Pódense crear ServidoresReais con caracteres especiais no nome
    new ServidorReal("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''", "imperio.dat"); // \ e " non
                                                                                   // permitidos

    assertAll("Comproba a creacion de Servidores",
        () -> assertEquals(clon1.obterNome(), clon2.obterNome()),

        () -> assertEquals(sLong.obterNome().length(), 500),
        () -> assertEquals(sLonger.obterNome().length(), 1000),

        () -> assertThrows(NullPointerException.class,
            () -> new ServidorReal("Imperiais", "nonExistingFile.dat")),

        () -> assertThrows(NullPointerException.class,
            () -> new ServidorReal("Imperiais", "testDictionary.dat")),

        () -> assertThrows(NullPointerException.class, () -> new ServidorReal("Imperiais", null)));

  }

  public void testStringServidorReal() {

    ServidorReal imperiais = new ServidorReal("Imperiais", "imperio.dat");

    Servidor rebeldes = new ServidorReal("Rebeldes", "rebeldes.dat");

    ServidorReal sNull = new ServidorReal(null, "imperio.dat");

    ServidorReal sEmoji = new ServidorReal("\uD83D\uDE02", "imperio.dat");

    ServidorReal sSymbols =
        new ServidorReal("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''", "imperio.dat"); // "\" and "
                                                                                       // " " not
                                                                                       // allowed

    assertAll("Comproba os metodos de obtencion de nome",
        // Os métodos "toString()" e "obterNome()" devolven o mesmo
        () -> assertEquals(imperiais.obterNome(), imperiais.toString()),
        () -> assertEquals(rebeldes.obterNome(), rebeldes.toString()),

        // Se creamos un Servidor co nome a nulo, os métodos de obtención de nome devolven nulo
        () -> assertNull(sNull.obterNome()), () -> assertNull(sNull.toString()),

        // Os métodos de obtención de nome funcionan correctamente cando o nome contén emojis ou
        // caracteres especiais
        () -> assertEquals(sEmoji.obterNome(), "\uD83D\uDE02"),
        () -> assertEquals(sEmoji.toString(), "\uD83D\uDE02"),

        // Pódense crear Servidores con caracteres especiais no nome
        () -> assertEquals(sSymbols.obterNome(), "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"),
        () -> assertEquals(sSymbols.toString(), "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"));

  }

  public void testMestresServidorReal() {

    ServidorReal imperiaisReal = new ServidorReal("Imperiais SR", "imperio.dat");
    Servidor imperiais = new ServidorReal("Imperiais", "imperio.dat");

    // O método "establecerMestre()" non fai nada
    imperiaisReal.establecerMestre(imperiais);
    imperiais.establecerMestre(imperiaisReal);
    imperiaisReal.establecerMestre(imperiaisReal);
    imperiais.establecerMestre(imperiais);

    // Pódense establecer nulos como mestre
    imperiaisReal.establecerMestre(null);
    imperiais.establecerMestre(null);

    assertAll("Comproba a xestion de mestres",
        // O método "obterMestre()" sempre devolve null
        () -> assertNull(imperiaisReal.obterMestre()), () -> assertNull(imperiais.obterMestre()));

  }

  public void testResolverServidorReal() {

    Individuo u = new Individuo("User");
    ServidorReal imperiaisReal = new ServidorReal("Imperiais SR", "imperio.dat");
    Servidor imperiais = new ServidorReal("Imperiais", "imperio.dat");
    ServidorReal rebeldesReal = new ServidorReal("Rebeldes SR", "rebeldes.dat");
    Servidor rebeldes = new ServidorReal("Rebeldes", "rebeldes.dat");

    assertAll("Comproba o metodo resolver",
        // Resolver funciona correctamente co seu respectivo ficheiro e cas chaves correctas
        () -> assertEquals(imperiaisReal.resolver(u, "vader"), "Darth Vader"),
        () -> assertEquals(imperiais.resolver(u, "vader"), "Darth Vader"),
        () -> assertEquals(rebeldesReal.resolver(u, "luke"), "Luke Skywalker"),
        () -> assertEquals(rebeldes.resolver(u, "luke"), "Luke Skywalker"),

        // Resolver devolve nulo se empregamos unha chave non existente
        () -> assertNull(imperiaisReal.resolver(u, "chave")),
        () -> assertNull(imperiais.resolver(u, "chave")),
        () -> assertNull(rebeldesReal.resolver(u, "chave")),
        () -> assertNull(rebeldes.resolver(u, "chave")),

        // Resolver devolve nulo se empregamos unha chave doutro ficheiro (que non esté no empregado
        // polo servidor)
        () -> assertNull(imperiaisReal.resolver(u, "luke")),
        () -> assertNull(imperiais.resolver(u, "luke")),
        () -> assertNull(rebeldesReal.resolver(u, "vader")),
        () -> assertNull(rebeldes.resolver(u, "vader")),

        // Non se poden resolver chaves nulas
        () -> assertThrows(NullPointerException.class, () -> imperiaisReal.resolver(u, null)),
        () -> assertThrows(NullPointerException.class, () -> imperiais.resolver(u, null)),
        () -> assertThrows(NullPointerException.class, () -> rebeldesReal.resolver(u, null)),
        () -> assertThrows(NullPointerException.class, () -> rebeldes.resolver(u, null)));

  }

}
