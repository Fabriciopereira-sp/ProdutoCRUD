package app;

import dao.ProdutoDAO;
import dao.ProdutoDAOImpl;
import model.Produto;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAOImpl();
        Scanner sc = new Scanner(System.in);

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

            try {
                switch (opcao) {
                    case 1 -> {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Preço: ");
                        double preco = sc.nextDouble();
                        System.out.print("Quantidade: ");
                        int qtd = sc.nextInt();
                        Produto p = new Produto(nome, preco, qtd);
                        dao.cadastrar(p);
                        System.out.println("Produto cadastrado!");
                    }
                    case 2 -> {
                        List<Produto> lista = dao.listar();
                        lista.forEach(System.out::println);
                    }
                    case 3 -> {
                        System.out.print("ID: ");
                        Long id = sc.nextLong();
                        Produto p = dao.pesquisarPorId(id);
                        System.out.println(p != null ? p : "Produto não encontrado.");
                    }
                    case 4 -> {
                        System.out.print("ID: ");
                        Long id = sc.nextLong();
                        sc.nextLine();
                        System.out.print("Novo nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Novo preço: ");
                        double preco = sc.nextDouble();
                        System.out.print("Nova quantidade: ");
                        int qtd = sc.nextInt();
                        Produto p = new Produto(id, nome, preco, qtd);
                        dao.atualizar(p);
                        System.out.println("Produto atualizado!");
                    }
                    case 5 -> {
                        System.out.print("ID: ");
                        Long id = sc.nextLong();
                        dao.remover(id);
                        System.out.println("Produto removido!");
                    }
                    case 6 -> {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        List<Produto> lista = dao.pesquisarPorNome(nome);
                        lista.forEach(System.out::println);
                    }
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);

        sc.close();
    }
}
