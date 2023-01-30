package gal.udc.fic.nameserver.servidor;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.jupiter.api.Assertions.*;
import gal.udc.fic.nameserver.usuario.Individuo;

/**
 * Conxunto de tests que proban a entidade ProxyCache e as súas funcionalidades: -
 * testCreacionProxyCache: Proba que se poidan crear ProxyCaches con diferentes parámetros de
 * entrada. - testStringProxyCache: Comproba o funcionamento dos métodos "obterNome()" e
 * "toString()" da entidade ProxyCache. - testMestresProxyCache: Proba a implementación dos métodos
 * "establecerMestre()" e "obterMestre()". - testResolverProxyCache: Proba a implementación do
 * método "resolver()".
 */
public class ProxyCacheTest extends TestCase {

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public ProxyCacheTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(ProxyCacheTest.class);
  }

  public void testCreacionProxyCache() {

    // Pódense crear ProxyCaches e Servidores
    ProxyCache imperiais = new ProxyCache("Imperiais", "imperio.dat");
    Servidor rebeldes = new ProxyCache("Rebeldes", "rebeldes.dat");

    // Pódense crear ProxyCaches co mesmo nome
    ProxyCache clon1 = new ProxyCache("Clon", "imperio.dat");
    ProxyCache clon2 = new ProxyCache("Clon", "imperio.dat");

    // Pódense crear ProxyCaches sen nome
    new ProxyCache(null, "imperio.dat");

    // Pódense crear ProxyCaches con nomes moi longos
    ProxyCache sLong = new ProxyCache(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eu imperdiet enim. Aenean gravida mattis tristique. Morbi luctus eros a mi maximus, id ornare mauris viverra. Integer blandit vehicula congue. Duis purus odio, scelerisque at convallis iaculis, dapibus eget dolor. Sed semper vestibulum lorem sed congue. Praesent egestas laoreet iaculis. Sed at ante ut enim rhoncus pulvinar."
            + "Vestibulum rhoncus sed nunc non rutrum. Praesent ac nunc sem. Aenean ac odio malesuada, faucibus nisl sit blandit.",
        "imperio.dat");
    ProxyCache sLonger = new ProxyCache(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas varius erat diam, eget laoreet lorem semper sit amet. Donec a lobortis nulla. Quisque nec felis in turpis facilisis imperdiet. Maecenas ante nisi, sollicitudin ac tellus vitae, pretium malesuada enim. Aliquam laoreet luctus blandit. Proin iaculis odio mauris, at ultricies lacus dictum sit amet. In sit amet suscipit nisi. Vivamus pretium purus mi, eu pulvinar lacus venenatis in. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean laoreet quam non lacus ornare, sed rutrum metus vulputate. Aliquam erat volutpat. Ut nec velit vitae nisi tincidunt maximus."
            + "Duis tincidunt lorem at diam sagittis egestas. Integer sed ultrices risus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras quis sapien eu neque accumsan feugiat eu nec leo. Sed volutpat tincidunt aliquet. Donec eget ullamcorper metus. In fermentum venenatis vulputate. Suspendisse tincidunt.",
        "imperio.dat");

    // Pódense crear ProxyCaches con emojis no nome
    new ProxyCache("\uD83D\uDE02", "imperio.dat");

    // Pódense crear ProxyCaches con caracteres especiais no nome
    new ProxyCache("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''", "imperio.dat"); // \ e " non
                                                                                 // permitidos

    // Sí se poden crear ProxyCaches con ficheiros que non existen
    new ProxyCache("Imperiais", "nonExistingFile.dat");

    // Non se poden crear ProxyCaches con ficheiros nulos
    new ProxyCache("Imperiais", null);

    assertAll("Comproba a creacion de ProxyCaches",
        () -> assertEquals(clon1.obterNome(), clon2.obterNome()),

        () -> assertEquals(sLong.obterNome().length(), 500 + 12),
        () -> assertEquals(sLonger.obterNome().length(), 1000 + 12));

  }

  public void testStringProxyCache() {

    ProxyCache imperiais = new ProxyCache("Imperiais", "imperio.dat");

    Servidor rebeldes = new ProxyCache("Rebeldes", "rebeldes.dat");

    ProxyCache pNull = new ProxyCache(null, "imperio.dat");

    ProxyCache pEmoji = new ProxyCache("\uD83D\uDE02", "imperio.dat");

    ProxyCache pSymbols =
        new ProxyCache("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''", "imperio.dat"); // "\" and " "
                                                                                     // " not
                                                                                     // allowed

    assertAll("Comproba os metodos de obtencion de nome",
        // Os métodos "toString()" e "obterNome()" devolven o mesmo
        () -> assertEquals(imperiais.obterNome(), imperiais.toString()),
        () -> assertEquals(imperiais.obterNome(), "Imperiais (con proxy)"),
        () -> assertEquals(rebeldes.obterNome(), rebeldes.toString()),

        // Se creamos un ProxyCache co nome a nulo, os métodos de obtención de nome devolven "null
        // (con proxy)"
        () -> assertEquals(pNull.obterNome(), "null (con proxy)"),
        () -> assertEquals(pNull.toString(), "null (con proxy)"),

        // Os métodos de obtención de nome funcionan correctamente cando o nome contén emojis ou
        // caracteres especiais
        () -> assertEquals(pEmoji.obterNome(), "\uD83D\uDE02 (con proxy)"),
        () -> assertEquals(pEmoji.toString(), "\uD83D\uDE02 (con proxy)"),

        // Pódense crear Servidores con caracteres especiais no nome
        () -> assertEquals(pSymbols.obterNome(),
            "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  '' (con proxy)"),
        () -> assertEquals(pSymbols.toString(),
            "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  '' (con proxy)"));

  }

  public void testMestresProxyCache() {

    ProxyCache imperiaisProxy = new ProxyCache("Imperiais PC", "imperio.dat");
    Servidor imperiais = new ProxyCache("Imperiais", "imperio.dat");

    // O método "establecerMestre()" non fai nada
    imperiaisProxy.establecerMestre(imperiais);
    imperiais.establecerMestre(imperiaisProxy);
    imperiaisProxy.establecerMestre(imperiaisProxy);
    imperiais.establecerMestre(imperiais);

    // Pódense establecer nulos como mestre
    imperiaisProxy.establecerMestre(null);
    imperiais.establecerMestre(null);

    assertAll("Comproba a xestion de mestres",
        // O método "obterMestre()" sempre devolve null
        () -> assertNull(imperiaisProxy.obterMestre()), () -> assertNull(imperiais.obterMestre()));

  }

  public void testResolverProxyCache() {

    Individuo u = new Individuo("User");
    ProxyCache imperiaisProxy = new ProxyCache("Imperiais PC", "imperio.dat");
    Servidor imperiais = new ProxyCache("Imperiais", "imperio.dat");
    ProxyCache rebeldesProxy = new ProxyCache("Rebeldes PC", "rebeldes.dat");
    Servidor rebeldes = new ProxyCache("Rebeldes", "rebeldes.dat");

    assertAll("Comproba o metodo resolver",
        // Resolver funciona correctamente co seu respectivo ficheiro e cas chaves correctas
        () -> assertEquals(imperiaisProxy.resolver(u, "vader"), "Darth Vader"),
        () -> assertEquals(imperiais.resolver(u, "vader"), "Darth Vader"),
        () -> assertEquals(rebeldesProxy.resolver(u, "luke"), "Luke Skywalker"),
        () -> assertEquals(rebeldes.resolver(u, "luke"), "Luke Skywalker"),

        // Resolver devolve nulo se empregamos unha chave non existente
        () -> assertNull(imperiaisProxy.resolver(u, "chave")),
        () -> assertNull(imperiais.resolver(u, "chave")),
        () -> assertNull(rebeldesProxy.resolver(u, "chave")),
        () -> assertNull(rebeldes.resolver(u, "chave")),

        // Resolver devolve nulo se empregamos unha chave doutro ficheiro (que non esté no empregado
        // polo servidor)
        () -> assertNull(imperiaisProxy.resolver(u, "luke")),
        () -> assertNull(imperiais.resolver(u, "luke")),
        () -> assertNull(rebeldesProxy.resolver(u, "vader")),
        () -> assertNull(rebeldes.resolver(u, "vader")),

        // Non se poden resolver chaves nulas
        () -> assertThrows(NullPointerException.class, () -> imperiaisProxy.resolver(u, null)),
        () -> assertThrows(NullPointerException.class, () -> imperiais.resolver(u, null)),
        () -> assertThrows(NullPointerException.class, () -> rebeldesProxy.resolver(u, null)),
        () -> assertThrows(NullPointerException.class, () -> rebeldes.resolver(u, null)));

  }

}
