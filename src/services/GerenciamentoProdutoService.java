package services;

import java.util.ArrayList;
import java.util.List;
import models.Produto;
import models.Categoria;

public class GerenciamentoProdutoService {
    private List<Produto> produtos = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public boolean atualizarPrecoProduto(int id, double preco) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                produto.setPreco(preco);
                return true;
            }
        }
        return false;
    }

    public boolean atualizarEstoqueProduto(int id, int quantidade) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                produto.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque() + quantidade);
                return true;
            }
        }
        return false;
    }
    
    public boolean removerProduto(int id) {
        return produtos.removeIf(produto -> produto.getId() == id);
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
}
