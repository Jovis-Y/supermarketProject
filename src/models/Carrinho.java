package models;
import java.util.HashMap;
import java.util.Map;

public class Carrinho {
    private Map<Produto, Integer> itens = new HashMap<>();

    public void adicionarProduto(Produto produto, int quantidade) {
        itens.put(produto, itens.getOrDefault(produto, 0) + quantidade);
    }

    public void removerProduto(Produto produto) {
        itens.remove(produto);
    }

    public double calcularTotal() {
        return itens.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPreco() * entry.getValue())
                .sum();
    }

    public Map<Produto, Integer> getItens() {
        return itens;
    }

    public void limpar() {

        itens.clear();

    }
}
