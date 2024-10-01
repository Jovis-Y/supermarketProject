package gui;

import javax.swing.*;

import models.Produto;

import controllers.ProdutoController;
import javax.swing.table.DefaultTableModel;
import java.util.List;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardScreen extends JFrame {
    private JPanel contentPanel;
    private ProdutoController produtoController;
    private JTable produtosTable;


    public DashboardScreen() {
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela
        setResizable(false); // Impede redimensionamento

        // Criação da barra de menu
        JMenuBar menuBar = new JMenuBar();

        // Menu Produtos
        JMenu menuProdutos = new JMenu("Produtos");
        JMenuItem itemProdutos = new JMenuItem("Gerenciar Produtos");

        // Ação para o menu Produtos
        itemProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProdutos();
            }
        });

        menuProdutos.add(itemProdutos);

        
        JMenu menuFuncionarios = new JMenu("Funcionários");
        JMenuItem itemListarFuncionarios = new JMenuItem("Listar Funcionários");
        JMenuItem itemAdicionarFuncionario = new JMenuItem("Adicionar Funcionário");
        JMenuItem itemRemoverFuncionario = new JMenuItem("Remover Funcionário");

        
        itemListarFuncionarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFuncionarios();
            }
        });

        itemAdicionarFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarFuncionario();
            }
        });

        itemRemoverFuncionario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerFuncionario();
            }
        });

        menuFuncionarios.add(itemListarFuncionarios);
        menuFuncionarios.add(itemAdicionarFuncionario);
        menuFuncionarios.add(itemRemoverFuncionario);

        
        JMenu menuClientes = new JMenu("Clientes");
        JMenuItem itemListarClientes = new JMenuItem("Listar Clientes");
        JMenuItem itemAdicionarCliente = new JMenuItem("Adicionar Cliente");
        JMenuItem itemRemoverCliente = new JMenuItem("Remover Cliente");

        
        itemListarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarClientes();
            }
        });

        itemAdicionarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarCliente();
            }
        });

        itemRemoverCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerCliente();
            }
        });

        menuClientes.add(itemListarClientes);
        menuClientes.add(itemAdicionarCliente);
        menuClientes.add(itemRemoverCliente);

        
        menuBar.add(menuProdutos);
        menuBar.add(menuFuncionarios);
        menuBar.add(menuClientes);

        
        setJMenuBar(menuBar);

        
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
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

    
        private void mostrarProdutos() {
        

        List<Produto> produtos = produtoController.listarProdutos(); 
        String[] columnNames = {"ID", "Nome", "Categoria", "Preço", "Estoque"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        
        for (Produto produto : produtos) {
            Object[] rowData = {produto.getId(), produto.getNome(), produto.getCategoria(), produto.getPreco(), produto.getQuantidadeEmEstoque()};
            model.addRow(rowData);
        }
    }

    
    private void mostrarFuncionarios() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Listando Funcionários...", JLabel.CENTER);
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void adicionarFuncionario() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Abrindo tela para adicionar funcionário...", JLabel.CENTER);
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void removerFuncionario() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Abrindo tela para remover funcionário...", JLabel.CENTER);
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    
    private void mostrarClientes() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Listando Clientes...", JLabel.CENTER);
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void adicionarCliente() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Abrindo tela para adicionar cliente...", JLabel.CENTER);
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void removerCliente() {
        contentPanel.removeAll();
        JLabel label = new JLabel("Abrindo tela para remover cliente...", JLabel.CENTER);
        contentPanel.add(label, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardScreen dashboard = new DashboardScreen();
            dashboard.setVisible(true);
        });
    }
}
