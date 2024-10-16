package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import models.Carrinho;
import models.Produto;

public class DashboardCliente extends JFrame {
    private JPanel contentPanel;
    private DefaultTableModel carrinhoTableModel;
    private Carrinho carrinho;
    private List<Produto> produtos;
    private JLabel totalLabel;
    private JTextField searchField;
    private JButton searchButton;

    public DashboardCliente() {
        setTitle("Dashboard Cliente");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane contentScrollPane = new JScrollPane(contentPanel);

        carrinhoTableModel = new DefaultTableModel(new Object[]{"Produto", "Quantidade", "Preço Total"}, 0);
        JTable carrinhoTable = new JTable(carrinhoTableModel);
        JScrollPane carrinhoScrollPane = new JScrollPane(carrinhoTable);
        carrinhoScrollPane.setBorder(BorderFactory.createTitledBorder("Carrinho"));

        totalLabel = new JLabel("Total: R$ 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.add(totalLabel, BorderLayout.EAST);

        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.setFont(new Font("Arial", Font.BOLD, 16));
        finalizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRecibo();
            }
        });

        JPanel carrinhoPanel = new JPanel(new BorderLayout());
        carrinhoPanel.add(carrinhoScrollPane, BorderLayout.CENTER);
        carrinhoPanel.add(totalPanel, BorderLayout.NORTH);
        carrinhoPanel.add(finalizarCompraButton, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, contentScrollPane, carrinhoPanel);
        splitPane.setDividerLocation(700);
        splitPane.setResizeWeight(1.0);
        splitPane.setEnabled(false);

        searchField = new JTextField(20);
        searchButton = new JButton("Buscar");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().toLowerCase();
                List<Produto> filteredProdutos = produtos.stream()
                        .filter(p -> p.getNome().toLowerCase().contains(query))
                        .collect(Collectors.toList());
                mostrarListaDeProdutos(filteredProdutos);
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Buscar Produto:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

        carrinho = new Carrinho();

        produtos = carregarProdutos("data/produtosEstocados.csv");
        mostrarListaDeProdutos(produtos);
    }

    private void mostrarListaDeProdutos(List<Produto> produtos) {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 colunas, espaçamento de 10px

        Dimension fixedSize = new Dimension(200, 200);

        for (Produto produto : produtos) {
            JPanel produtoPanel = new JPanel();
            produtoPanel.setLayout(new GridLayout(4, 1));
            produtoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            produtoPanel.setPreferredSize(fixedSize);
            produtoPanel.setMinimumSize(fixedSize);
            produtoPanel.setMaximumSize(fixedSize);

            JLabel nomeLabel = new JLabel("Nome: " + produto.getNome());
            JLabel precoLabel = new JLabel("Preço: R$ " + produto.getPreco());
            JLabel estoqueLabel = new JLabel("Estoque: " + produto.getQuantidadeEmEstoque());
            JButton maisButton = new JButton("Adicionar ao carrinho");

            maisButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int quantidadeNoCarrinho = carrinho.getItens().getOrDefault(produto, 0);
                    if (quantidadeNoCarrinho < produto.getQuantidadeEmEstoque()) {
                        carrinho.adicionarProduto(produto, 1);
                        atualizarCarrinho();
                    } else {
                        JOptionPane.showMessageDialog(null, "Quantidade em estoque insuficiente para " + produto.getNome());
                    }
                }
            });

            produtoPanel.add(nomeLabel);
            produtoPanel.add(precoLabel);
            produtoPanel.add(estoqueLabel);
            produtoPanel.add(maisButton);

            contentPanel.add(produtoPanel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void atualizarCarrinho() {
        carrinhoTableModel.setRowCount(0);
        double total = carrinho.calcularTotal();
        for (Map.Entry<Produto, Integer> entry : carrinho.getItens().entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            double precoTotal = produto.getPreco() * quantidade;
            carrinhoTableModel.addRow(new Object[]{produto.getNome(), quantidade, String.format("R$ %.2f", precoTotal)});
        }
        totalLabel.setText(String.format("Total: R$ %.2f", total));
    }

    private List<Produto> carregarProdutos(String filePath) {
        List<Produto> produtos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String nome = values[1];
                String categoria = values[2];
                double preco = Double.parseDouble(values[3]);
                int quantidadeEmEstoque = Integer.parseInt(values[4]);
                produtos.add(new Produto(id, nome, categoria, preco, quantidadeEmEstoque));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    private void mostrarRecibo() {
        JDialog reciboDialog = new JDialog(this, "Recibo", true);
        reciboDialog.setSize(400, 300);
        reciboDialog.setLayout(new BorderLayout());

        JTextArea reciboTextArea = new JTextArea();
        reciboTextArea.setEditable(false);
        StringBuilder reciboText = new StringBuilder("Recibo de Compra:\n\n");
        double total = 0.0;

        for (Map.Entry<Produto, Integer> entry : carrinho.getItens().entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            double precoTotal = produto.getPreco() * quantidade;
            reciboText.append(String.format("%s - Quantidade: %d - Preço Total: R$ %.2f\n", produto.getNome(), quantidade, precoTotal));
            total += precoTotal;
        }

        reciboText.append(String.format("\nTotal: R$ %.2f", total));
        reciboTextArea.setText(reciboText.toString());

        reciboDialog.add(new JScrollPane(reciboTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton confirmarButton = new JButton("Confirmar");
        JButton cancelarButton = new JButton("Cancelar");

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Compra finalizada com sucesso!");
                carrinho.limpar();
                atualizarCarrinho();
                reciboDialog.dispose();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reciboDialog.dispose();
            }
        });

        buttonPanel.add(confirmarButton);
        buttonPanel.add(cancelarButton);

        reciboDialog.add(buttonPanel, BorderLayout.SOUTH);
        reciboDialog.setLocationRelativeTo(this);
        reciboDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashboardCliente dashboard = new DashboardCliente();
            dashboard.setVisible(true);
        });
    }
}