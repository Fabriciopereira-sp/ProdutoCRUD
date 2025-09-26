package dao;

import factory.ConnectionFactory;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {

    @Override
    public void cadastrar(Produto produto) throws DaoException {
        final String sql = "INSERT INTO PRODUTOS (NOME, PRECO, QUANTIDADE) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setBigDecimal(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Falha ao cadastrar produto: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new DaoException("Erro nos dados do produto: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DaoException("Erro inesperado ao cadastrar: " + e.getMessage(), e);
        }
    }

    @Override
    public Produto pesquisarPorId(Long id) throws DaoException {
        final String sql = "SELECT ID, NOME, PRECO, QUANTIDADE FROM PRODUTOS WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        } catch (SQLException e) {
            throw new DaoException("Falha ao pesquisar produto por ID=" + id + ": " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DaoException("Erro inesperado ao pesquisar por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Produto> listar() throws DaoException {
        final String sql = "SELECT ID, NOME, PRECO, QUANTIDADE FROM PRODUTOS ORDER BY ID";
        final List<Produto> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(map(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw new DaoException("Falha ao listar produtos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DaoException("Erro inesperado ao listar: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Produto produto) throws DaoException {
        final String sql = "UPDATE PRODUTOS SET NOME = ?, PRECO = ?, QUANTIDADE = ? WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setBigDecimal(2, produto.getPreco());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setLong(4, produto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Falha ao atualizar produto ID=" + produto.getId() + ": " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DaoException("Erro inesperado ao atualizar: " + e.getMessage(), e);
        }
    }

    @Override
    public void remover(Long id) throws DaoException {
        final String sql = "DELETE FROM PRODUTOS WHERE ID = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Falha ao remover produto ID=" + id + ": " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DaoException("Erro inesperado ao remover: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Produto> pesquisarPorNome(String nome) throws DaoException {
        final String sql =
                "SELECT ID, NOME, PRECO, QUANTIDADE FROM PRODUTOS " +
                        "WHERE UPPER(NOME) LIKE UPPER(?) ORDER BY NOME";
        final List<Produto> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(map(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw new DaoException("Falha ao pesquisar por nome '" + nome + "': " + e.getMessage(), e);
        } catch (Exception e) {
            throw new DaoException("Erro inesperado ao pesquisar por nome: " + e.getMessage(), e);
        }
    }

    // ===== helper =====
    private Produto map(ResultSet rs) throws SQLException {
        return new Produto(
                rs.getLong("ID"),
                rs.getString("NOME"),
                rs.getBigDecimal("PRECO"),
                rs.getInt("QUANTIDADE")
        );
    }
}
