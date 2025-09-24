package app;

import dao.ProdutoDAO;
import dao.ProdutoDAOImpl;
import model.Produto;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            ProdutoDAO dao = new ProdutoDAOImpl();
            int opcao;

            do {
                System.out.println("\n===== MENU PRODUTOS =====");
                System.out.println("1 - Cadastrar");
                System.out.println("2 - Listar");
                System.out.println("3 - Pesquisar por ID");
                System.out.println("4 - Atualizar");
                System.out.println("5 - Remover");
                System.out.println("6 - Pesquisar por Nome");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Preço: ");
                        double preco = sc.nextDouble();
                        System.out.print("Quantidade: ");
                        int quantidade = sc.nextInt();
                        sc.nextLine();
                        Produto p = new Produto(null, nome, preco, quantidade);
                        dao.cadastrar(p);
                        System.out.println("Produto cadastrado com sucesso!");
                        break;
                    case 2:
                        List<Produto> lista = dao.listar();
                        if (lista.isEmpty()) {
                            System.out.println("Nenhum produto cadastrado.");
                        } else {
                            lista.forEach(System.out::println);
                        }
                        break;
                    case 3:
                        System.out.print("ID: ");
                        Long idBusca = sc.nextLong();
                        sc.nextLine();
                        Produto encontrado = dao.pesquisarPorId(idBusca);
                        System.out.println(encontrado != null ? encontrado : "Produto não encontrado.");
                        break;
                    case 4:
                        System.out.print("ID do produto para atualizar: ");
                        Long idAtualizar = sc.nextLong();
                        sc.nextLine();
                        Produto prodAtualizar = dao.pesquisarPorId(idAtualizar);
                        if (prodAtualizar == null) {
                            System.out.println("Produto não encontrado.");
                            break;
                        }
                        System.out.print("Novo Nome: ");
                        prodAtualizar.setNome(sc.nextLine());
                        System.out.print("Novo Preço: ");
                        prodAtualizar.setPreco(sc.nextDouble());
                        System.out.print("Nova Quantidade: ");
                        prodAtualizar.setQuantidade(sc.nextInt());
                        sc.nextLine();
                        dao.atualizar(prodAtualizar);
                        System.out.println("Produto atualizado com sucesso!");
                        break;
                    case 5:
                        System.out.print("ID do produto para remover: ");
                        Long idRemover = sc.nextLong();
                        sc.nextLine();
                        dao.remover(idRemover);
                        System.out.println("Produto removido com sucesso!");
                        break;
                    case 6:
                        System.out.print("Nome para pesquisar: ");
                        String nomePesquisa = sc.nextLine();
                        List<Produto> resultados = dao.pesquisarPorNome(nomePesquisa);
                        if (resultados.isEmpty()) {
                            System.out.println("Nenhum produto encontrado.");
                        } else {
                            resultados.forEach(System.out::println);
                        }
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } while (opcao != 0);
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
