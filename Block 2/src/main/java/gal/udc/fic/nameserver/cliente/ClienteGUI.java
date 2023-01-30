package gal.udc.fic.nameserver.cliente;

import gal.udc.fic.nameserver.servidor.Servidor;
import gal.udc.fic.nameserver.usuario.Usuario;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class ClienteGUI extends JFrame implements Cliente {

  public ClienteGUI() {
    servidorActual = null;
    comboModelServidores = new DefaultComboBoxModel();

    setJMenuBar(menuBar());
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(panelConsulta(), BorderLayout.CENTER);
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent event) {
        exitApp();
      }
    });
    pack();
    //
    Dimension screenDim = getToolkit().getScreenSize();
    setLocation((screenDim.width - getSize().width) / 2, (screenDim.height - getSize().height) / 2);
    setVisible(true);
    //
    conectar(null);
  }

  public Dimension getPreferredSize() {
    return new Dimension(850, 550);
  }

  private JPanel panelConsulta() {
    JPanel panel = new JPanel(new GridBagLayout());
    //
    // -- Nome: [________________] [Procurar]
    JPanel panelNome = new JPanel(new GridBagLayout());
    panelNome.add(new JLabel("Nome:"),
        gridBagConstraints(GridBagConstraints.CENTER, GridBagConstraints.NONE, 0.0, 1.0, 0, 0));
    textNome = new JTextField();
    panelNome.add(textNome, gridBagConstraints(GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, 1.0, 1.0, 1, 0));
    JButton botonNome = new JButton("Procurar");
    panelNome.add(botonNome,
        gridBagConstraints(GridBagConstraints.CENTER, GridBagConstraints.NONE, 0.0, 1.0, 2, 0));
    //
    // -- Servidor: [----------|\/|]
    JPanel panelServidor = new JPanel(new GridBagLayout());
    panelServidor.add(new JLabel("Servidor:"),
        gridBagConstraints(GridBagConstraints.LINE_START, GridBagConstraints.NONE, 0.0, 1.0, 0, 0));
    comboServidores = new JComboBox(comboModelServidores);
    panelServidor.add(comboServidores, gridBagConstraints(GridBagConstraints.LINE_END,
        GridBagConstraints.HORIZONTAL, 1.0, 1.0, 1, 0));
    //
    // -- Resultado: -----------------
    JPanel panelResultado = new JPanel(new GridBagLayout());
    panelResultado.add(new JLabel("Resultado:"),
        gridBagConstraints(GridBagConstraints.LINE_START, GridBagConstraints.NONE, 0.0, 1.0, 0, 0));
    etiquetaResultado = new JLabel("");
    panelResultado.add(etiquetaResultado, gridBagConstraints(GridBagConstraints.LINE_END,
        GridBagConstraints.HORIZONTAL, 1.0, 1.0, 1, 0));
    //
    // -- Usuario: -----------------
    JPanel panelUsuario = new JPanel(new GridBagLayout());
    etiquetaUsuario = new JLabel("");
    etiquetaUsuario.setHorizontalAlignment(SwingConstants.CENTER);
    panelUsuario.add(etiquetaUsuario, gridBagConstraints(GridBagConstraints.CENTER,
        GridBagConstraints.HORIZONTAL, 1.0, 1.0, 0, 0));
    //
    // --
    panel.add(panelUsuario, gridBagConstraints(GridBagConstraints.NORTH,
        GridBagConstraints.HORIZONTAL, 1.0, 0.0, 0, 0));
    panel.add(panelServidor, gridBagConstraints(GridBagConstraints.NORTH,
        GridBagConstraints.HORIZONTAL, 1.0, 0.0, 0, 1));
    panel.add(panelNome, gridBagConstraints(GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
        1.0, 0.0, 0, 2));
    panel.add(panelResultado,
        gridBagConstraints(GridBagConstraints.NORTH, GridBagConstraints.BOTH, 1.0, 1.0, 0, 3));
    //
    comboServidores.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        servidorActual = (Servidor) ((JComboBox) e.getSource()).getSelectedItem();
      }
    });
    botonNome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClienteGUI.this.buscar();
      }
    });
    textNome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ClienteGUI.this.buscar();
      }
    });
    return panel;
  }

  private JMenuBar menuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("Ficheiro");
    JMenu ferramentasMenu = new JMenu("Ferramentas");
    JMenu axudaMenu = new JMenu("Axuda");
    menuBar.add(fileMenu);
    menuBar.add(ferramentasMenu);
    menuBar.add(axudaMenu);
    JMenuItem sairAction = new JMenuItem("Saír");
    fileMenu.add(sairAction);
    JMenuItem configurarAction = new JMenuItem("Configurar");
    ferramentasMenu.add(configurarAction);
    JMenuItem sobreAction = new JMenuItem("Sobre");
    axudaMenu.add(sobreAction);
    sairAction.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        ClienteGUI.this.exitApp();
      }
    });
    configurarAction.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        new ConfigurarDialog(ClienteGUI.this, comboModelServidores);
      }
    });
    sobreAction.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        JOptionPane.showMessageDialog(ClienteGUI.this, "Servidor de nomes", "Sobre",
            JOptionPane.INFORMATION_MESSAGE);
      }
    });
    return menuBar;
  }

  public void engadirServidor(Servidor servidor) {
    comboModelServidores.addElement(servidor);
  }

  public void conectar(Usuario u) {
    usuario = u;
    if (usuario != null) {
      etiquetaUsuario.setText("Conectado como " + usuario);
    } else {
      etiquetaUsuario.setText("Ningún usuario conectado");
    }
  }

  private void buscar() {
    String nome = textNome.getText().trim();
    if ((!nome.equals("")) && (servidorActual != null) && (usuario != null)) {
      setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
      etiquetaResultado.setText("Procurando...");
      String resultado = servidorActual.resolver(usuario, nome);
      setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
      if (resultado != null) {
        etiquetaResultado.setText(resultado);
      } else {
        etiquetaResultado.setText("-- Non houbo resultados --");
      }
    }
  }

  private GridBagConstraints gridBagConstraints(int anchor, int fill, double weightx,
      double weighty, int gridx, int gridy) {
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.anchor = anchor;
    gridBagConstraints.fill = fill;
    gridBagConstraints.weightx = weightx;
    gridBagConstraints.weighty = weighty;
    gridBagConstraints.gridx = gridx;
    gridBagConstraints.gridy = gridy;
    gridBagConstraints.insets = new Insets(8, 8, 8, 8);
    return gridBagConstraints;
  }

  private void exitApp() {
    System.out.println("Homer, use the fork!");
    dispose();
    System.exit(0);
  }

  private JTextField textNome;
  private JComboBox comboServidores;
  private JLabel etiquetaResultado;
  private JLabel etiquetaUsuario;
  private DefaultComboBoxModel comboModelServidores;
  private Servidor servidorActual;
  private Usuario usuario;

}



class ConfigurarDialog extends JDialog {
  public ConfigurarDialog(JFrame owner, ListModel listaServidores) {
    super(owner, "Configurar servidores", true);
    servidores = new Servidor[listaServidores.getSize()];
    for (int i = 0; i < listaServidores.getSize(); i++) {
      servidores[i] = (Servidor) listaServidores.getElementAt(i);
    }
    tableModel = new AbstractTableModel() {
      public int getColumnCount() {
        return 2;
      }

      public int getRowCount() {
        return ConfigurarDialog.this.servidores.length;
      }

      public String getColumnName(int col) {
        switch (col) {
          case 0:
            return "Servidor";
          case 1:
            return "Mestre";
          default:
            return "????";
        }
      }

      public Object getValueAt(int row, int col) {
        Servidor servidor = ConfigurarDialog.this.servidores[row];
        switch (col) {
          case 0:
            return servidor;
          case 1:
            return servidor.obterMestre();
          default:
            return null;
        }
      }
    };

    setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        ConfigurarDialog.this.pechar();
      }
    });

    JPanel panelBotons = new JPanel();
    panelBotons.setLayout(new BoxLayout(panelBotons, BoxLayout.LINE_AXIS));
    JButton botonEstablecerMestre = new JButton("Establecer");
    panelBotons.add(botonEstablecerMestre);
    JButton botonQuitarMestre = new JButton("Quitar");
    panelBotons.add(botonQuitarMestre);
    panelBotons.add(Box.createHorizontalGlue());
    JPanel panelPechar = new JPanel();
    panelPechar.setLayout(new BoxLayout(panelPechar, BoxLayout.LINE_AXIS));
    panelPechar.add(Box.createHorizontalGlue());
    JButton botonPechar = new JButton("Pechar");
    panelPechar.add(botonPechar);

    Container panel = getContentPane();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    taboa = new JTable(tableModel);
    taboa.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    panel.add(new JScrollPane(taboa));
    panel.add(panelBotons);
    panel.add(panelPechar);

    botonPechar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ConfigurarDialog.this.pechar();
      }
    });
    botonEstablecerMestre.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ConfigurarDialog.this.establecerMestre(true);
      }
    });
    botonQuitarMestre.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ConfigurarDialog.this.establecerMestre(false);
      }
    });

    pack();
    setLocationRelativeTo(owner);
    setVisible(true);
  }

  private void pechar() {
    setVisible(false);
  }

  private void establecerMestre(boolean novo) {
    int idx = taboa.getSelectedRow();
    if (idx == -1) {
      JOptionPane.showMessageDialog(ConfigurarDialog.this, "Debes escoller o servidor primeiro.",
          "Erro", JOptionPane.ERROR_MESSAGE);
      return;
    }
    Servidor servidor = (Servidor) tableModel.getValueAt(idx, 0);
    Servidor mestre = null;
    if (novo) {
      mestre = (Servidor) JOptionPane.showInputDialog(this,
          "Escolle o mestre para o servidor " + servidor, "Establecer mestre",
          JOptionPane.QUESTION_MESSAGE, null, servidores, servidores[0]);
      if (mestre == null) {
        return;
      }
    }
    servidor.establecerMestre(mestre);
    tableModel.fireTableCellUpdated(idx, 1);
  }

  private Servidor servidores[];
  private AbstractTableModel tableModel;
  private JTable taboa;
}
