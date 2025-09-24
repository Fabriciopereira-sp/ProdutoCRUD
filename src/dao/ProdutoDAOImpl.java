package dao;

import factory.ConnectionFactory;
import model.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {

    private Connection conn;

    public ProdutoDAOImpl() throws Exception {
        conn = ConnectionFactory.getConnection();
    }

    @Override
    public void cadastrar(Produto produto) throws Exception {
        String sql = "INSERT INTO PRODUTO (ID, NOME, PRECO, QUANTIDADE) VALUES (SEQ_PRODUTO.NEXTVAL, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setDouble(2, produto.getPreco());
        stmt.setInt(3, produto.getQuantidade());
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public Produto pesquisarPorId(Long id) throws Exception {
        String sql = "SELECT * FROM PRODUTO WHERE ID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        Produto produto = null;
        if (rs.next()) {
            produto = new Produto(rs.getLong("ID"), rs.getString("NOME"), rs.getDouble("PRECO"), rs.getInt("QUANTIDADE"));
        }
        rs.close();
        stmt.close();
        return produto;
    }

    @Override
    public List<Produto> listar() throws Exception {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUTO";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            Produto produto = new Produto(rs.getLong("ID"), rs.getString("NOME"), rs.getDouble("PRECO"), rs.getInt("QUANTIDADE"));
            lista.add(produto);
        }
        rs.close();
        stmt.close();
        return lista;
    }

    @Override
    public void atualizar(Produto produto) throws Exception {
        String sql = "UPDATE PRODUTO SET NOME=?, PRECO=?, QUANTIDADE=? WHERE ID=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setDouble(2, produto.getPreco());
        stmt.setInt(3, produto.getQuantidade());
        stmt.setLong(4, produto.getId());
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public void remover(Long id) throws Exception {
        String sql = "DELETE FROM PRODUTO WHERE ID=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public List<Produto> pesquisarPorNome(String nome) throws Exception {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUTO WHERE LOWER(NOME) LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + nome.toLowerCase() + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Produto produto = new Produto(rs.getLong("ID"), rs.getString("NOME"), rs.getDouble("PRECO"), rs.getInt("QUANTIDADE"));
            lista.add(produto);
        }
        rs.close();
        stmt.close();
        return lista;
    }
}
