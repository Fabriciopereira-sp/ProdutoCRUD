package app;

import dao.DaoException;
import dao.ProdutoDAO;
import dao.ProdutoDAOImpl;
import model.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ProdutoDAO dao = new ProdutoDAOImpl();

    public static void main(String[] args) {
        int opcao;
        do {
            menu();
            opcao = lerInt("Escolha: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrar();
                    case 2 -> listar();
                    case 3 -> pesquisarPorId();
                    case 4 -> atualizar();
                    case 5 -> remover();
                    case 6 -> pesquisarPorNome();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (DaoException e) {
                System.out.println("[DAO] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[ERRO] " + e.getMessage());
            }
            if (opcao != 0) {
                System.out.println("\nENTER para continuar...");
                sc.nextLine();
                limparConsole();
            }
        } while (opcao != 0);

        sc.close();
    }

    private static void menu() {
        System.out.println("""
                \n===== MENU PRODUTOS =====
                1 - Cadastrar
                2 - Listar
                3 - Pesquisar por ID
                4 - Atualizar
                5 - Remover
                6 - Pesquisar por Nome
                0 - Sair
                """);
    }

    // ===== Operações =====

    private static void cadastrar() throws DaoException {
        String nome = lerTexto("Nome: ");
        BigDecimal preco = lerMoney("Preço (ex: 99,90): ");
        int qtd = lerIntMin("Quantidade (>= 0): ", 0);

        Produto p = new Produto(nome, preco, qtd);
        dao.cadastrar(p);
        System.out.println("✔ Produto cadastrado!");
    }

    private static void listar() throws DaoException {
        List<Produto> lista = dao.listar();
        if (lista.isEmpty()) System.out.println("Nenhum produto cadastrado.");
        else lista.forEach(System.out::println);
    }

    private static void pesquisarPorId() throws DaoException {
        long id = lerLongMin("ID: ", 1);
        Produto p = dao.pesquisarPorId(id);
        System.out.println(p != null ? p : "Produto não encontrado.");
    }

    private static void atualizar() throws DaoException {
        long id = lerLongMin("ID do produto: ", 1);
        Produto atual = dao.pesquisarPorId(id);
        if (atual == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        System.out.println("Atual: " + atual);

        String nome = lerTexto("Novo nome: ");
        BigDecimal preco = lerMoney("Novo preço: ");
        int qtd = lerIntMin("Nova quantidade: ", 0);

        Produto p = new Produto(id, nome, preco, qtd);
        dao.atualizar(p);
        System.out.println("✔ Produto atualizado!");
    }

    private static void remover() throws DaoException {
        long id = lerLongMin("ID: ", 1);
        dao.remover(id);
        System.out.println("✔ Produto removido (se existia).");
    }

    private static void pesquisarPorNome() throws DaoException {
        String nome = lerTexto("Parte do nome: ");
        List<Produto> lista = dao.pesquisarPorNome(nome);
        if (lista.isEmpty()) System.out.println("Nada encontrado.");
        else lista.forEach(System.out::println);
    }

    private static int lerInt(String label) {
        while (true) {
            try {
                System.out.print(label);
                String s = sc.nextLine().trim();
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static long lerLongMin(String label, long min) {
        while (true) {
            try {
                System.out.print(label);
                String s = sc.nextLine().trim();
                long v = Long.parseLong(s);
                if (v < min) {
                    System.out.println("Valor mínimo: " + min);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static int lerIntMin(String label, int min) {
        while (true) {
            try {
                System.out.print(label);
                String s = sc.nextLine().trim();
                int v = Integer.parseInt(s);
                if (v < min) {
                    System.out.println("Valor mínimo: " + min);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static BigDecimal lerMoney(String label) {
        while (true) {
            try {
                System.out.print(label);
                String s = sc.nextLine().trim().replace(",", ".");
                BigDecimal v = new BigDecimal(s);
                if (v.signum() < 0) {
                    System.out.println("Preço não pode ser negativo.");
                    continue;
                }
                // arredonda com HALF_UP para evitar ArithmeticException (ex.: 10,999)
                return v.setScale(2, RoundingMode.HALF_UP);
            } catch (Exception e) {
                System.out.println("Valor monetário inválido.");
            }
        }
    }

    private static String lerTexto(String label) {
        while (true) {
            System.out.print(label);
            String s = sc.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.println("Campo obrigatório.");
        }
    }

    private static void limparConsole() {
        try { System.out.print("\033[H\033[2J"); System.out.flush(); }
        catch (Exception ignored) {}
    }
}
