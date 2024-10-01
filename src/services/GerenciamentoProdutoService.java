package services;

import models.Produto;
import models.Categoria;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GerenciamentoProdutoService {
    private static final String CSV_FILE_PATH = "data/produtosEstocados.csv";
    private List<Produto> produtos = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();

    public GerenciamentoProdutoService() {
        carregarProdutos();
    }

    private void carregarProdutos() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String nome = values[1];
                String categoria = values[2];
                double preco = Double.parseDouble(values[3]);
                int quantidade = Integer.parseInt(values[4]);
                Produto produto = new Produto(id, nome, categoria, preco, quantidade);
                produtos.add(produto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salvarProdutos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (Produto produto : produtos) {
                String line = produto.getId() + "," + produto.getNome() + "," + produto.getPreco() + "," + produto.getQuantidadeEmEstoque();
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public boolean atualizarEstoqueProduto(int id, int quantidade) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() + quantidade);
                salvarProdutos();
                return true;
            }
        }
        return false;
    }

    public boolean atualizarPrecoProduto(int id, double preco) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                produto.setPreco(preco);
                salvarProdutos();
                return true;
            }
        }
        return false;   
    }

    public boolean removerProduto(int id) {
        boolean removed = produtos.removeIf(produto -> produto.getId() == id);
        if (removed) {
            salvarProdutos();
        }
        return removed;
    }

    public void adicionarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    public boolean produtoExiste(int id) {
        return produtos.stream().anyMatch(produto -> produto.getId() == id);
    }

    public List<Categoria> listarCategorias() {
        return new ArrayList<>(categorias);
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        salvarProdutos();
    }
}