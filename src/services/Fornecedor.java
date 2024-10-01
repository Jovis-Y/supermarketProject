package services;

import java.util.ArrayList;
import java.util.List;

import models.Produto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Fornecedor {
    private int id;
    private String nome;
    private String endereco;
    private String contato;
    private String caminhoArquivo;
    protected List<Produto> produtos;

    public Fornecedor(int id, String nome, String endereco, String contato, String caminhoArquivo) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.contato = contato;
        this.caminhoArquivo = caminhoArquivo;
        this.produtos = new ArrayList<>();
    }

    public void carregarProdutos() {
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] campos = linha.split(",");
                if (campos.length == 4) {
                    int id = Integer.parseInt(campos[0]);
                    String nome = campos[1];
                    String categoria = campos[2];
                    double preco = Double.parseDouble(campos[3]);
                    Produto produto = new Produto(id, nome, categoria, preco, 1000);
                    produtos.add(produto);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}