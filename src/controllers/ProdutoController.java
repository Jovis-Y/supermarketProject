package controllers;

import models.Produto;
import services.GerenciamentoProdutoService;

import java.util.List;

public class ProdutoController {
    private GerenciamentoProdutoService produtoService = new GerenciamentoProdutoService();


    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    public void adicionarProduto(Produto produto) {
        produtoService.adicionarProduto(produto);
    }

    public void adicionarProduto(Produto produto, double taxa) {
        Produto produtoComTaxa = new Produto(produto);
        produtoComTaxa.setPreco(produto.getPreco() * (1 + taxa / 100));
        produtoService.adicionarProduto(produtoComTaxa);
    }

    public boolean produtoExiste(int id) {
        return produtoService.produtoExiste(id);
    }

    public boolean atualizarPrecoProduto(int id, double preco) {
        return produtoService.atualizarPrecoProduto(id, preco);  
    }

    public boolean atualizarEstoqueProduto(int id, int quantidade) {
        return produtoService.atualizarEstoqueProduto(id, quantidade);  
    }

    public boolean removerProduto(int id) {
        return produtoService.removerProduto(id);  
    }
}
