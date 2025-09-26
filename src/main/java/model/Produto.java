package model;

import java.math.BigDecimal;

public class Produto {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;

    public Produto() {}

    public Produto(Long id, String nome, BigDecimal preco, Integer quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Produto(String nome, BigDecimal preco, Integer quantidade) {
        this(null, nome, preco, quantidade);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    @Override
    public String toString() {
        return "Produto {" +
                "ID = " + id +
                ",Nome = '" + nome + '\'' +
                ",Preco = " + (preco == null ? null : preco.toPlainString()) +
                ",Quantidade = " + quantidade +
                '}';
    }
}
