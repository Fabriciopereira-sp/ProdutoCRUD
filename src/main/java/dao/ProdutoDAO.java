package dao;

import model.Produto;
import java.util.List;

public interface ProdutoDAO {
    void cadastrar(Produto produto) throws DaoException;
    Produto pesquisarPorId(Long id) throws DaoException;
    List<Produto> listar() throws DaoException;
    void atualizar(Produto produto) throws DaoException;
    void remover(Long id) throws DaoException;
    List<Produto> pesquisarPorNome(String nome) throws DaoException;
}
