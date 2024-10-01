package gui.panels;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import models.Produto;
import controllers.ProdutoController;
import java.util.List;

public class PainelProdutos implements PainelExibicao {
    private ProdutoController produtoController = new ProdutoController();

    @Override
    public JPanel mostrar() {
        List<Produto> produtos = produtoController.listarProdutos();
        String[] columnNames = {"ID", "Nome", "Categoria", "Preço", "Estoque"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Produto produto : produtos) {
            Object[] rowData = {
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.getQuantidadeEmEstoque()
            };
            model.addRow(rowData);
        }

        JTable produtosTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(produtosTable);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}
