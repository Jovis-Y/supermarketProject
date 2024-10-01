package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.panels.PainelExibicao;
import gui.panels.PainelProdutos;
import gui.panels.PainelFuncionarios;
import gui.panels.PainelClientes;

public class DashboardScreen extends JFrame {
    private JPanel contentPanel;

    public DashboardScreen() {
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Cria a barra de menu
        JMenuBar menuBar = new JMenuBar();

        // Configura menus
        JMenu menuProdutos = new JMenu("Produtos");
        JMenuItem itemProdutos = new JMenuItem("Listar Produtos");
        itemProdutos.addActionListener(new MenuActionListener(new PainelProdutos()));
        menuProdutos.add(itemProdutos);

        JMenu menuFuncionarios = new JMenu("Funcionários");
        JMenuItem itemFuncionarios = new JMenuItem("Listar Funcionários");
        itemFuncionarios.addActionListener(new MenuActionListener(new PainelFuncionarios()));
        menuFuncionarios.add(itemFuncionarios);

        JMenu menuClientes = new JMenu("Clientes");
        JMenuItem itemClientes = new JMenuItem("Listar Clientes");
        itemClientes.addActionListener(new MenuActionListener(new PainelClientes()));
        menuClientes.add(itemClientes);

        // Adiciona menus à barra de menu
        menuBar.add(menuProdutos);
        menuBar.add(menuFuncionarios);
        menuBar.add(menuClientes);

        setJMenuBar(menuBar);

        // Configura o painel de conteúdo
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        mostrarTelaInicial();
    }

    private void mostrarTelaInicial() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Bem-vindo ao Dashboard!", JLabel.CENTER);
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private class MenuActionListener implements ActionListener {
        private PainelExibicao painel;

        public MenuActionListener(PainelExibicao painel) {
            this.painel = painel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            contentPanel.removeAll();
            contentPanel.add(painel.mostrar(), BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardScreen dashboard = new DashboardScreen();
            dashboard.setVisible(true);
        });
    }
}
