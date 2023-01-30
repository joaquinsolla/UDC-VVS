package gal.udc.fic.nameserver.usuario;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Conxunto de tests que proban a entidade Grupo e as súas funcionalidades: - testCreacionGrupo:
 * Proba que se poidan crear usuarios con diferentes parámetros de entrada. - testStringGrupo:
 * Comproba o funcionamento dos métodos "obterNome()" e "toString()" da entidade Grupo. -
 * testAutorizarGrupo: Proba a implementación do método "autorizar()". - testMembrosGrupo: Proba a
 * xestión de membros cos métodos "engadirMembro()" e "eliminarMembro()".
 */
public class GrupoTest extends TestCase {

  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public GrupoTest(String testName) {
    super(testName);
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(GrupoTest.class);
  }

  public void testCreacionGrupo() {

    // Pódense crear grupos
    Grupo grupo1 = new Grupo("Grupo 1");
    Grupo grupo2 = new Grupo("Grupo 2");

    // Pódense crear grupos co mesmo nome
    Grupo clon1 = new Grupo("Grupo clon");
    Grupo clon2 = new Grupo("Grupo clon");

    // Pódense crear grupos sen nome
    new Grupo(null);

    // Pódense crear grupos con nomes moi longos
    Grupo gLong = new Grupo(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eu imperdiet enim. Aenean gravida mattis tristique. Morbi luctus eros a mi maximus, id ornare mauris viverra. Integer blandit vehicula congue. Duis purus odio, scelerisque at convallis iaculis, dapibus eget dolor. Sed semper vestibulum lorem sed congue. Praesent egestas laoreet iaculis. Sed at ante ut enim rhoncus pulvinar."
            + "Vestibulum rhoncus sed nunc non rutrum. Praesent ac nunc sem. Aenean ac odio malesuada, faucibus nisl sit blandit.");

    Grupo gLonger = new Grupo(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas varius erat diam, eget laoreet lorem semper sit amet. Donec a lobortis nulla. Quisque nec felis in turpis facilisis imperdiet. Maecenas ante nisi, sollicitudin ac tellus vitae, pretium malesuada enim. Aliquam laoreet luctus blandit. Proin iaculis odio mauris, at ultricies lacus dictum sit amet. In sit amet suscipit nisi. Vivamus pretium purus mi, eu pulvinar lacus venenatis in. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Aenean laoreet quam non lacus ornare, sed rutrum metus vulputate. Aliquam erat volutpat. Ut nec velit vitae nisi tincidunt maximus."
            + "Duis tincidunt lorem at diam sagittis egestas. Integer sed ultrices risus. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras quis sapien eu neque accumsan feugiat eu nec leo. Sed volutpat tincidunt aliquet. Donec eget ullamcorper metus. In fermentum venenatis vulputate. Suspendisse tincidunt.");

    // Pódense crear grupos con emojis no nome
    new Grupo("\uD83D\uDE02");

    // Pódense crear grupos con caracteres especiais no nome
    new Grupo("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"); // \ e " non permitidos

    assertAll("Comproba a creacion de Grupos",
        () -> assertEquals(clon1.obterNome(), clon2.obterNome()),

        () -> assertNotEquals(grupo1.obterID(), grupo2.obterID()),
        () -> assertNotEquals(clon1.obterID(), clon2.obterID()),

        () -> assertEquals(gLong.obterNome().length(), 500),
        () -> assertEquals(gLonger.obterNome().length(), 1000));

  }

  public void testStringGrupo() {

    Grupo grupo = new Grupo("Grupo");

    Grupo gNull = new Grupo(null);

    Grupo gEmoji = new Grupo("\uD83D\uDE02");

    Grupo gSymbols = new Grupo("!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"); // "\" and " " " not
                                                                              // allowed

    assertAll("Comproba os metodos de obtencion de nome",
        // Os métodos "toString()" e "obterNome()" devolven o mesmo
        () -> assertEquals(grupo.obterNome(), grupo.toString()),

        // Se creamos un grupo co nome a nulo, os métodos de obtención de nome devolven nulo
        () -> assertNull(gNull.obterNome()), () -> assertNull(gNull.toString()),

        // Os métodos de obtención de nome funcionan correctamente cando o nome contén emojis ou
        // caracteres especiais
        () -> assertEquals(gEmoji.obterNome(), "\uD83D\uDE02"),
        () -> assertEquals(gEmoji.toString(), "\uD83D\uDE02"),

        // Pódense crear usuarios con caracteres especiais no nome
        () -> assertEquals(gSymbols.obterNome(), "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"),
        () -> assertEquals(gSymbols.toString(), "!·%&/()=?¿¿¡^*¨Ç_:;áÁ{}[]üÜ<>|@#~€¬$ºª  ''"));

  }

  public void testMembrosGrupo() {

    Individuo u1 = new Individuo("Usuario 1");
    Individuo u2 = new Individuo("Usuario 2");
    Individuo u3 = new Individuo("Usuario 3");
    Individuo u4 = new Individuo("Usuario 4");

    Grupo g1 = new Grupo("Grupo 1");
    Grupo g2 = new Grupo("Grupo 2");
    Grupo g3 = new Grupo("Grupo 3");

    // Pódense engadir usuarios como membros
    g1.engadirMembro(u1);
    g1.engadirMembro(u2);
    g1.engadirMembro(u3);
    g1.engadirMembro(u4);

    g2.engadirMembro(u1);

    // Pódese engadir varias veces o mesmo membro
    g1.engadirMembro(u1);
    g1.engadirMembro(u1);
    g1.engadirMembro(u1);

    // Pódense engadir outros grupos como membros
    g1.engadirMembro(g2);
    g1.engadirMembro(g3);

    // Pódese engadir a sí mesmo como membro
    g1.engadirMembro(g1);

    // Pódense engadir nulos como membro
    g1.engadirMembro(null);

    // Pódense eliminar membros
    g1.eliminarMembro(u1);
    g1.eliminarMembro(u2);
    g1.eliminarMembro(u3);
    g1.eliminarMembro(u4);

    g2.eliminarMembro(u1);

    // Pódese eliminar un membro que non figura no grupo
    g3.eliminarMembro(u1);

    // Pódense eliminar os grupos que son membros
    g1.eliminarMembro(g1);
    g1.eliminarMembro(g2);
    g1.eliminarMembro(g3);

    // Pódense eliminar nulos
    g1.eliminarMembro(null);

  }

  public void testAutorizarGrupo() {

    Individuo u1 = new Individuo("Usuario 1");
    Individuo u2 = new Individuo("Usuario 2");
    Individuo u3 = new Individuo("Usuario 3");
    Individuo u4 = new Individuo("Usuario 4");

    Grupo g1 = new Grupo("Grupo 1");
    Grupo g2 = new Grupo("Grupo 2");
    Grupo g3 = new Grupo("Grupo 3");

    g1.engadirMembro(u1);
    g1.engadirMembro(u2);
    g1.engadirMembro(u3);

    g2.engadirMembro(u4);

    g3.engadirMembro(g1);
    g3.engadirMembro(g2);

    // Ciclo de membros
    Grupo ciclo1 = new Grupo("Ciclo 1");
    Grupo ciclo2 = new Grupo("Ciclo 2");
    Grupo ciclo3 = new Grupo("Ciclo 3");

    ciclo1.engadirMembro(u1);
    ciclo2.engadirMembro(u1);
    ciclo3.engadirMembro(u1);
    ciclo1.engadirMembro(ciclo2);
    ciclo2.engadirMembro(ciclo3);
    ciclo3.engadirMembro(ciclo1);

    assertAll("Comproba o metodo Autorizar",
        // Autorizar devolve true se o usuario é membro do grupo, do contrario devolve false
        () -> assertTrue(g1.autorizar(u1)), () -> assertTrue(g1.autorizar(u2)),
        () -> assertTrue(g1.autorizar(u3)), () -> assertFalse(g1.autorizar(u4)),

        // Un grupo non pode autorizar a outro grupo que é membro (só autoriza usuarios)
        () -> assertFalse(g3.autorizar(g1)), () -> assertFalse(g3.autorizar(g3)),

        // Se un grupo ten como membro outros grupos con usuarios, autorizar tamén devolverá true
        // estes últimos
        () -> assertTrue(g3.autorizar(u1)), () -> assertTrue(g3.autorizar(u2)),
        () -> assertTrue(g3.autorizar(u3)), () -> assertTrue(g3.autorizar(u4)),

        // Proba cun ciclo de membros
        () -> assertTrue(ciclo1.autorizar(u1)),

        // Non se pode autorizar a un nulo
        () -> assertThrows(NullPointerException.class, () -> g1.autorizar(null)));

  }

}
