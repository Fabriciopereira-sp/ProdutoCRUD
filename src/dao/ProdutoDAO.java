package dao;

import model.Produto;
import java.util.List;

public interface ProdutoDAO {
    void cadastrar(Produto produto) throws Exception;
    Produto pesquisarPorId(Long id) throws Exception;
    List<Produto> listar() throws Exception;
    void atualizar(Produto produto) throws Exception;
    void remover(Long id) throws Exception;
    List<Produto> pesquisarPorNome(String nome) throws Exception;
}
