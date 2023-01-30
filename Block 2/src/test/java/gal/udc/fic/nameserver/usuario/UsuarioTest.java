package gal.udc.fic.nameserver.usuario;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Conxunto de tests que proban a entidade Usuario e as súas funcionalidades: - testCreacionUsuario:
 * Proba que se poidan crear usuarios con diferentes parámetros de entrada. - testStringUsuario:
 * Comproba o funcionamento dos métodos "obterNome()" e "toString()" da entidade Usuario. -
 * testMembrosUsuario: Proba a xestión de membros cos métodos "engadirMembro()" e
 * "eliminarMembro()". - testAutorizarUsuario: Proba a implementación do método "autorizar()".
 */
public class UsuarioTest extends TestCase {

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public UsuarioTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(UsuarioTest.class);
  }

  public void testCreacionUsuario() {

    // Pódense crear usuarios e individuos
    Usuario ui = new Individuo("Usuario individuo");
    Individuo ii = new Individuo("Individuo individuo");

    // Pódense crear usuarios co mesmo nome
    Usuario clon1 = new Individuo("Clon");
    Usuario clon2 = new Individuo("Clon");

    // Pódense crear usuarios sen nome
    new Individuo(null);

    // Pódense crear usuarios con nomes moi longos
    Usuario uLong = new Individuo(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eu imperdiet enim. Aenean gravida mattis tristique. Morbi luctus eros a mi maximus, id ornare mauris viverra. Integer blandit vehicula congue. Duis purus odio, scelerisque at convallis iaculis, dapibus eget dolor. Sed semper vestibulum lorem sed congue. Praesent egestas laoreet iaculis. Sed at ante ut enim rhoncus pulvinar."
            + "Vestibulum rhoncus sed nunc non rutrum. Praesent ac nunc sem. Aenean ac odio malesuada, faucibus nisl sit blandit.");

    Usuario uLonger = new Individuo(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas varius erat diam, eget laoreet lorem semper sit amet. Donec a lobortis nulla. Quisque nec felis in turpis facilisis imperdiet. Maecenas ante nisi, sollicitudin ac tellus vitae, pretium malesuada enim. Aliquam laoreet luctus blandit. Proin iaculis odio mauris, at ultricies lacus dictum sit amet. In sit amet suscipit nisi. Vivamus pretium purus mi, eu pulvinar lacus venenatis in. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean laoreet quam non lacus ornare, sed rutrum metus vulputate. Aliquam erat volutpat. Ut nec velit vitae nisi tincidunt maximus."
            + "Duis tincidunt lorem at diam sagittis egestas. Integer sed ultrices risus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras quis sapien eu neque accumsan feugiat eu nec leo. Sed volutpat tincidunt aliquet. Donec eget ullamcorper metus. In fermentum venenatis vulputate. Suspendisse tincidunt.");

    // Pódense crear usuarios con emojis no nome
    new Individuo("\uD83D\uDE02");

    // Pódense crear usuarios con caracteres especiais no nome
    new Individuo("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"); // \ e " non permitidos

    assertAll("Comproba a creacion de Usuarios",
        () -> assertEquals(clon1.obterNome(), clon2.obterNome()),

        () -> assertNotEquals(ui.obterID(), ii.obterID()),
        () -> assertNotEquals(clon1.obterID(), clon2.obterID()),

        () -> assertEquals(uLong.obterNome().length(), 500),
        () -> assertEquals(uLonger.obterNome().length(), 1000));

  }

  public void testStringUsuario() {

    Usuario ui = new Individuo("Usuario individuo");
    Individuo ii = new Individuo("Individuo individuo");

    Usuario uNull = new Individuo(null);

    Usuario uEmoji = new Individuo("\uD83D\uDE02");

    Usuario uSymbols = new Individuo("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"); // "\" and " " "
                                                                                    // not allowed

    assertAll("Comproba a os metodos de obtencion de nome",
        // Os métodos "toString()" e "obterNome()" devolven o mesmo
        () -> assertEquals(ui.obterNome(), ui.toString()),
        () -> assertEquals(ii.obterNome(), ii.toString()),

        // Se creamos un usuario co nome a nulo, os métodos de obtención de nome devolven nulo
        () -> assertNull(uNull.obterNome()), () -> assertNull(uNull.toString()),

        // Os métodos de obtención de nome funcionan correctamente cando o nome contén emojis ou
        // caracteres especiais
        () -> assertEquals(uEmoji.obterNome(), "\uD83D\uDE02"),
        () -> assertEquals(uEmoji.toString(), "\uD83D\uDE02"),

        // Pódense crear usuarios con caracteres especiais no nome e este retornase correctamente
        () -> assertEquals(uSymbols.obterNome(), "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"),
        () -> assertEquals(uSymbols.toString(), "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''")

    );

  }

  public void testMembrosUsuario() {

    Usuario u0 = new Individuo("Usuario individuo");
    Individuo u1 = new Individuo("Usuario usuario");

    // Pódese chamar a "engadirMembro" e "eliminarMembro" dende un usuario, pero non fai nada
    // Tamén se pode engadir varias veces o mesmmo membro e eliminar membros que non figuran
    u0.engadirMembro(u0);
    u0.engadirMembro(u1);
    u0.engadirMembro(u1);
    u1.engadirMembro(u1);
    u1.engadirMembro(u0);
    u1.engadirMembro(u0);

    u0.eliminarMembro(u0);
    u0.eliminarMembro(u0);
    u0.eliminarMembro(u1);
    u1.eliminarMembro(u1);
    u1.eliminarMembro(u1);
    u1.eliminarMembro(u0);

    // Pódense pasar grupos como membros
    Grupo g = new Grupo("Group");
    u0.engadirMembro(g);
    u0.eliminarMembro(g);
    u1.engadirMembro(g);
    u1.eliminarMembro(g);

    // Pódese pasar un nulo como membro
    u0.engadirMembro(null);
    u0.eliminarMembro(null);
    u1.engadirMembro(null);
    u1.eliminarMembro(null);

  }

  public void testAutorizarUsuario() {

    Usuario u0 = new Individuo("Usuario individuo");

    Individuo u1 = new Individuo("Usuario usuario");

    Grupo g = new Grupo("Group");

    assertAll("Comproba o metodo autorizar usuario",
        // Autorizar so devolve true cando ambos ids son iguais
        () -> assertTrue(u0.autorizar(u0)), () -> assertTrue(u1.autorizar(u1)),
        () -> assertFalse(u0.autorizar(u1)), () -> assertFalse(u1.autorizar(u0)),

        // Non se pode autorizar a un nulo
        () -> assertThrows(NullPointerException.class, () -> u1.autorizar(null)),

        // Un usuario pode autorizar a un grupo (pero se os ids son diferentes devolve false
        // igualmente)
        () -> assertFalse(u0.autorizar(g)), () -> assertFalse(u1.autorizar(g)));

  }

}
