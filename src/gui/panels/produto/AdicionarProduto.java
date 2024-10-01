package gui.panels.produto;

import services.Fornecedor;
import models.Produto;
import controllers.ProdutoController;

import javax.swing.*;

import gui.panels.PainelExibicao;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdicionarProduto implements PainelExibicao {
    private List<Fornecedor> fornecedores;
    private Fornecedor fornecedorSelecionado;
    private JComboBox<String> comboFornecedores;
    private JTextArea txtProdutos;
    private JTextField txtId;
    private JTextField txtQuantidade;
    private ProdutoController produtoController = new ProdutoController();
    private Produto produtoSelecionado;

    public AdicionarProduto(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    @Override
    public JPanel mostrar() {
        JPanel panel = new JPanel(new BorderLayout());

        // ComboBox para selecionar fornecedor
        comboFornecedores = new JComboBox<>();
        for (Fornecedor fornecedor : fornecedores) {
            comboFornecedores.addItem(fornecedor.getNome());
        }
        comboFornecedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeFornecedor = (String) comboFornecedores.getSelectedItem();
                fornecedorSelecionado = fornecedores.stream()
                        .filter(f -> f.getNome().equals(nomeFornecedor))
                        .findFirst()
                        .orElse(null);
                carregarProdutosFornecedor();
            }
        });

        // Exibir produtos do fornecedor
        txtProdutos = new JTextArea(10, 30);
        txtProdutos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtProdutos);

        // Campos de entrada
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel lblId = new JLabel("ID do Produto:");
        txtId = new JTextField();
        JLabel lblQuantidade = new JLabel("Quantidade:");
        txtQuantidade = new JTextField();

        // Botão de adicionar
        JButton btnAdicionar = new JButton("Adicionar Produto");
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txtId.getText());
                int quantidade = Integer.parseInt(txtQuantidade.getText());

                // Lógica para adicionar o produto ao estoque
                boolean produtoEncontrado = false;
                for (Produto produto : fornecedorSelecionado.getProdutos()) {
                    if (produto.getId() == id) {

                        produtoSelecionado = new Produto(produto);
                        produtoEncontrado = true;
                        break;
                    }
                }

                if (produtoEncontrado) {
                    JOptionPane.showMessageDialog(panel, "Produto adicionado com sucesso!");

                    // Atualizar a lista de produtos
                    produtoSelecionado.setQuantidadeEmEstoque(quantidade);
                    produtoController.adicionarProduto(produtoSelecionado);

                } else {
                    JOptionPane.showMessageDialog(panel, "Produto não encontrado!");
                }

                // Limpar campos após adicionar
                txtId.setText("");
                txtQuantidade.setText("");


            }  
        });

        // Adicionar componentes ao painel de entrada
        inputPanel.add(lblId);
        inputPanel.add(txtId);
        inputPanel.add(lblQuantidade);
        inputPanel.add(txtQuantidade);
        inputPanel.add(new JLabel()); // Espaço vazio
        inputPanel.add(btnAdicionar);

        // Adicionar componentes ao painel principal
        panel.add(comboFornecedores, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void carregarProdutosFornecedor() {
        if (fornecedorSelecionado != null) {
            fornecedorSelecionado.carregarProdutos();
            txtProdutos.setText("");
            for (Produto produto : fornecedorSelecionado.getProdutos()) {
                txtProdutos.append("ID: " + produto.getId() + ", Nome: " + produto.getNome() + ", Categoria: " + produto.getCategoria() + ", Preço: " + produto.getPreco() + "\n");
            }
        }
    }
}