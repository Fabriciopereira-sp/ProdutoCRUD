package dao;

import factory.ConnectionFactory;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {

    @Override
    public void cadastrar(Produto produto) throws Exception {
        String sql = "INSERT INTO PRODUTOS (NOME, PRECO, QUANTIDADE) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.executeUpdate();
        }
    }

    @Override
    public Produto pesquisarPorId(Long id) throws Exception {
        String sql = "SELECT * FROM PRODUTOS WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Produto(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO"),
                        rs.getInt("QUANTIDADE")
                );
            }
        }
        return null;
    }

    @Override
    public List<Produto> listar() throws Exception {
        String sql = "SELECT * FROM PRODUTOS";
        List<Produto> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Produto(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO"),
                        rs.getInt("QUANTIDADE")
                ));
            }
        }
        return lista;
    }

    @Override
    public void atualizar(Produto produto) throws Exception {
        String sql = "UPDATE PRODUTOS SET NOME = ?, PRECO = ?, QUANTIDADE = ? WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setLong(4, produto.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void remover(Long id) throws Exception {
        String sql = "DELETE FROM PRODUTOS WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Produto> pesquisarPorNome(String nome) throws Exception {
        String sql = "SELECT * FROM PRODUTOS WHERE LOWER(NOME) LIKE LOWER(?)";
        List<Produto> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Produto(
                        rs.getLong("ID"),
                        rs.getString("NOME"),
                        rs.getDouble("PRECO"),
                        rs.getInt("QUANTIDADE")
                ));
            }
        }
        return lista;
    }
}
