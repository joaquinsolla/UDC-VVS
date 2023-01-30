package gal.udc.fic.nameserver.servidor;

import gal.udc.fic.nameserver.usuario.Usuario;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class ServidorReal implements Servidor {

  public ServidorReal(String nome, String ficheiro) {
    this.nome = nome;
    // úsase unha táboa hash para almacenar os pares
    // (chave,valor) atopdos no ficheiro indicado
    dicionario = new Hashtable<String, String>();
    cargaDicionario(ficheiro);
  }

  private void cargaDicionario(String ficheiro) {
    try {
      String pathFicheiro = getClass().getClassLoader().getResource(ficheiro).getPath();
      BufferedReader f = new BufferedReader(new FileReader(pathFicheiro));
      // O formato do ficheiro debe ser:
      // <code>
      // clave1\n
      // valor1\n
      // clave2\n
      // valor2\n
      // ...
      // claven\n
      // valorn\n
      // </code>
      String chave = f.readLine();
      while (chave != null) {
        dicionario.put(chave, f.readLine());
        chave = f.readLine();
      }

      f.close();

    } catch (FileNotFoundException e) {
      System.err.println("Ficheiro " + ficheiro + " non atopado: dicionario baleiro");
    } catch (IOException e) {
      System.err.println("Erro na lectura do dicionario " + ficheiro + ": " + e.getMessage());
    }
  }

  public String obterNome() {
    return nome;
  }

  public String resolver(Usuario usuario, String chave) {
    // resolución de nome mediante procura na táboa hash;
    // para simular unha operación custosa, penalizamos a
    // operación con 5 segundos de agarda
    try {
      Thread.sleep(5000);
    } catch (Exception e) {
    }
    return dicionario.get(chave);
  }

  public Servidor obterMestre() {
    return null;
  }

  public void establecerMestre(Servidor mestre) {}

  public String toString() {
    return obterNome();
  }

  private Hashtable<String, String> dicionario;
  private String nome;

}
