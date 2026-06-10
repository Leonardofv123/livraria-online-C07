package br.inatel;

import br.inatel.daos.*;
import br.inatel.models.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static AutorDAO     autorDAO     = new AutorDAO();
    static CategoriaDAO categoriaDAO = new CategoriaDAO();
    static ClienteDAO   clienteDAO   = new ClienteDAO();
    static LivroDAO     livroDAO     = new LivroDAO();
    static PedidoDAO    pedidoDAO    = new PedidoDAO();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║     LIVRARIA ONLINE - MENU       ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Gerenciar Autores            ║");
            System.out.println("║  2. Gerenciar Categorias         ║");
            System.out.println("║  3. Gerenciar Livros             ║");
            System.out.println("║  4. Gerenciar Clientes           ║");
            System.out.println("║  5. Gerenciar Pedidos            ║");
            System.out.println("║  6. Relatórios com JOIN          ║");
            System.out.println("║  7. Popular banco com dados      ║");
            System.out.println("║  0. Sair                         ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Escolha: ");
            opcao = lerInt();

            switch (opcao) {
                case 1 -> menuAutor();
                case 2 -> menuCategoria();
                case 3 -> menuLivro();
                case 4 -> menuCliente();
                case 5 -> menuPedido();
                case 6 -> menuRelatorios();
                case 7 -> popularBanco();
                case 0 -> System.out.println("Encerrando... Até logo!");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
        sc.close();
    }

    static void menuAutor() {
        System.out.println("\n── AUTORES ──────────────────────────");
        System.out.println("1. Inserir  2. Atualizar  3. Deletar  4. Buscar por nome  5. Listar todos");
        System.out.print("Escolha: ");
        int op = lerInt();
        switch (op) {
            case 1 -> {
                System.out.print("Nome: ");         String nome = sc.nextLine();
                System.out.print("Nacionalidade: "); String nac  = sc.nextLine();
                System.out.print("Data nascimento (AAAA-MM-DD): "); String dt = sc.nextLine();
                System.out.print("Biografia: ");    String bio  = sc.nextLine();
                System.out.println(autorDAO.insertAutor(new Autor(nome, nac, dt, bio))
                        ? "✔ Autor inserido!" : "✘ Erro ao inserir.");
            }
            case 2 -> {
                System.out.print("Nome do autor: ");         String nome = sc.nextLine();
                System.out.print("Nova nacionalidade: ");    String nac  = sc.nextLine();
                System.out.print("Nova biografia: ");        String bio  = sc.nextLine();
                System.out.println(autorDAO.updateAutor(nome, nac, bio)
                        ? "✔ Autor atualizado!" : "✘ Autor não encontrado.");
            }
            case 3 -> {
                System.out.print("Nome do autor a deletar: "); String nome = sc.nextLine();
                System.out.println(autorDAO.deleteAutor(nome)
                        ? "✔ Autor deletado!" : "✘ Erro ao deletar.");
            }
            case 4 -> {
                System.out.print("Nome do autor: "); String nome = sc.nextLine();
                Autor a = autorDAO.selectAutor(nome);
                if (a != null) {
                    System.out.println("\nNome:          " + a.getNome());
                    System.out.println("Nacionalidade: " + a.getNacionalidade());
                    System.out.println("Nascimento:    " + a.getData_nascimento());
                    System.out.println("Biografia:     " + a.getBiografia());
                } else System.out.println("Autor não encontrado.");
            }
            case 5 -> {
                List<Autor> lista = autorDAO.selectTodosAutores();
                System.out.printf("%n%-25s %-12s %-12s%n", "Nome", "Nac.", "Nascimento");
                System.out.println("-".repeat(55));
                for (Autor a : lista)
                    System.out.printf("%-25s %-12s %-12s%n", a.getNome(), a.getNacionalidade(), a.getData_nascimento());
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    static void menuCategoria() {
        System.out.println("\n── CATEGORIAS ───────────────────────");
        System.out.println("1. Inserir  2. Atualizar  3. Deletar  4. Buscar por nome  5. Listar todas");
        System.out.print("Escolha: ");
        int op = lerInt();
        switch (op) {
            case 1 -> {
                System.out.print("Nome: ");      String nome = sc.nextLine();
                System.out.print("Descrição: "); String desc = sc.nextLine();
                System.out.println(categoriaDAO.insertCategoria(new Categoria(nome, desc))
                        ? "✔ Categoria inserida!" : "✘ Erro ao inserir.");
            }
            case 2 -> {
                System.out.print("Nome da categoria: "); String nome = sc.nextLine();
                System.out.print("Nova descrição: ");    String desc = sc.nextLine();
                System.out.println(categoriaDAO.updateCategoria(nome, desc)
                        ? "✔ Categoria atualizada!" : "✘ Categoria não encontrada.");
            }
            case 3 -> {
                System.out.print("Nome da categoria a deletar: "); String nome = sc.nextLine();
                System.out.println(categoriaDAO.deleteCategoria(nome)
                        ? "✔ Categoria deletada!" : "✘ Erro ao deletar.");
            }
            case 4 -> {
                System.out.print("Nome da categoria: "); String nome = sc.nextLine();
                Categoria c = categoriaDAO.selectCategoria(nome);
                if (c != null) {
                    System.out.println("\nNome:      " + c.getNome());
                    System.out.println("Descrição: " + c.getDescricao());
                } else System.out.println("Categoria não encontrada.");
            }
            case 5 -> {
                List<Categoria> lista = categoriaDAO.selectCategorias();
                System.out.printf("%n%-25s %s%n", "Nome", "Descrição");
                System.out.println("-".repeat(75));
                for (Categoria c : lista)
                    System.out.printf("%-25s %s%n", c.getNome(), c.getDescricao());
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    static void menuLivro() {
        System.out.println("\n── LIVROS ───────────────────────────");
        System.out.println("1. Inserir  2. Atualizar  3. Deletar  4. Buscar por título  5. Listar todos");
        System.out.print("Escolha: ");
        int op = lerInt();
        switch (op) {
            case 1 -> {
                System.out.print("Título: ");         String tit  = sc.nextLine();
                System.out.print("ISBN: ");           String isbn = sc.nextLine();
                System.out.print("Descrição: ");      String desc = sc.nextLine();
                System.out.print("Estoque: ");        int est     = lerInt();
                System.out.print("Ano publicação: "); int ano     = lerInt();
                System.out.print("Preço: R$ ");       float preco = lerFloat();
                System.out.println(livroDAO.insertLivro(new Livro(tit, isbn, desc, est, ano, preco))
                        ? "✔ Livro inserido!" : "✘ Erro ao inserir.");
            }
            case 2 -> {
                System.out.print("Título do livro: "); String tit  = sc.nextLine();
                System.out.print("Novo preço: R$ ");   float preco = lerFloat();
                System.out.print("Novo estoque: ");    int est     = lerInt();
                Livro l = livroDAO.selectLivroPorTitulo(tit);
                if (l != null) {
                    System.out.println(livroDAO.updateLivro(new Livro(tit, l.getIsbn(), l.getDescricao(), est, l.getAno_publicacao(), preco))
                            ? "✔ Livro atualizado!" : "✘ Erro ao atualizar.");
                } else System.out.println("Livro não encontrado.");
            }
            case 3 -> {
                System.out.print("Título do livro a deletar: "); String tit = sc.nextLine();
                System.out.println(livroDAO.deleteLivro(tit)
                        ? "✔ Livro deletado!" : "✘ Erro ao deletar.");
            }
            case 4 -> {
                System.out.print("Título do livro: "); String tit = sc.nextLine();
                Livro l = livroDAO.selectLivroPorTitulo(tit);
                if (l != null) {
                    System.out.println("\nTítulo:    " + l.getTitulo());
                    System.out.println("ISBN:      " + l.getIsbn());
                    System.out.println("Preço:     R$" + l.getPreco());
                    System.out.println("Estoque:   " + l.getEstoque());
                    System.out.println("Ano:       " + l.getAno_publicacao());
                    System.out.println("Descrição: " + l.getDescricao());
                } else System.out.println("Livro não encontrado.");
            }
            case 5 -> {
                List<Livro> lista = livroDAO.selectLivro();
                System.out.printf("%n%-35s %-8s %-8s %s%n", "Título", "Ano", "Estoque", "Preço");
                System.out.println("-".repeat(65));
                for (Livro l : lista)
                    System.out.printf("%-35s %-8d %-8d R$%.2f%n", l.getTitulo(), l.getAno_publicacao(), l.getEstoque(), l.getPreco());
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    static void menuCliente() {
        System.out.println("\n── CLIENTES ─────────────────────────");
        System.out.println("1. Inserir  2. Atualizar  3. Deletar  4. Buscar por CPF  5. Listar todos");
        System.out.print("Escolha: ");
        int op = lerInt();
        switch (op) {
            case 1 -> {
                System.out.print("Nome: ");              String nome  = sc.nextLine();
                System.out.print("CPF: ");               String cpf   = sc.nextLine();
                System.out.print("Email: ");             String email = sc.nextLine();
                System.out.print("Telefone: ");          String tel   = sc.nextLine();
                System.out.print("Data nascimento (AAAA-MM-DD): "); String dt = sc.nextLine();
                System.out.println(clienteDAO.insertCliente(new Cliente(nome, cpf, email, tel, dt))
                        ? "✔ Cliente inserido!" : "✘ Erro ao inserir.");
            }
            case 2 -> {
                System.out.print("CPF do cliente: ");  String cpf   = sc.nextLine();
                System.out.print("Novo email: ");      String email = sc.nextLine();
                System.out.print("Novo telefone: ");   String tel   = sc.nextLine();
                System.out.println(clienteDAO.updateCliente(cpf, email, tel)
                        ? "✔ Cliente atualizado!" : "✘ Cliente não encontrado.");
            }
            case 3 -> {
                System.out.print("CPF do cliente a deletar: "); String cpf = sc.nextLine();
                System.out.println(clienteDAO.deleteCliente(cpf)
                        ? "✔ Cliente deletado!" : "✘ Erro ao deletar.");
            }
            case 4 -> {
                System.out.print("CPF do cliente: "); String cpf = sc.nextLine();
                Cliente c = clienteDAO.selectClientePorCpf(cpf);
                if (c != null) {
                    System.out.println("\nNome:       " + c.getNome());
                    System.out.println("CPF:        " + c.getCpf());
                    System.out.println("Email:      " + c.getEmail());
                    System.out.println("Telefone:   " + c.getTelefone());
                    System.out.println("Nascimento: " + c.getData_nascimento());
                } else System.out.println("Cliente não encontrado.");
            }
            case 5 -> {
                List<Cliente> lista = clienteDAO.selectCliente();
                System.out.printf("%n%-35s %-14s %s%n", "Nome", "CPF", "Email");
                System.out.println("-".repeat(75));
                for (Cliente c : lista)
                    System.out.printf("%-35s %-14s %s%n", c.getNome(), c.getCpf(), c.getEmail());
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    static void menuPedido() {
        System.out.println("\n── PEDIDOS ──────────────────────────");
        System.out.println("1. Inserir  2. Atualizar  3. Deletar  4. Buscar por ID  5. Listar todos");
        System.out.print("Escolha: ");
        int op = lerInt();
        switch (op) {
            case 1 -> {
                System.out.print("ID do cliente: "); int    id  = lerInt();
                System.out.print("Status: ");        String sts = sc.nextLine();
                System.out.print("Observação: ");    String obs = sc.nextLine();
                System.out.print("Total: R$ ");      float  tot = lerFloat();
                System.out.println(pedidoDAO.insertPedido(new Pedido(id, sts, obs, tot))
                        ? "✔ Pedido inserido!" : "✘ Erro ao inserir.");
            }
            case 2 -> {
                System.out.print("ID do cliente: ");    int    id  = lerInt();
                System.out.print("Novo status: ");      String sts = sc.nextLine();
                System.out.print("Nova observação: ");  String obs = sc.nextLine();
                System.out.print("Novo total: R$ ");    float  tot = lerFloat();
                System.out.println(pedidoDAO.updatePedido(new Pedido(id, sts, obs, tot))
                        ? "✔ Pedido atualizado!" : "✘ Pedido não encontrado.");
            }
            case 3 -> {
                System.out.print("ID do pedido a deletar: "); int id = lerInt();
                System.out.println(pedidoDAO.deletePedido(id)
                        ? "✔ Pedido deletado!" : "✘ Erro ao deletar.");
            }
            case 4 -> {
                System.out.print("ID do pedido: "); int id = lerInt();
                Pedido p = pedidoDAO.selectPedido(id);
                if (p != null) {
                    System.out.println("\nID Cliente:  " + p.getId_cliente());
                    System.out.println("Status:      " + p.getStatus());
                    System.out.println("Total:       R$" + p.getTotal());
                    System.out.println("Observação:  " + p.getObservacao());
                } else System.out.println("Pedido não encontrado.");
            }
            case 5 -> {
                List<Pedido> lista = pedidoDAO.selectTodosPedidos();
                System.out.printf("%n%-10s %-12s %-10s %s%n", "ID Cliente", "Status", "Total", "Observação");
                System.out.println("-".repeat(70));
                for (Pedido p : lista)
                    System.out.printf("%-10d %-12s R$%-8.2f %s%n", p.getId_cliente(), p.getStatus(), p.getTotal(), p.getObservacao());
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    static void menuRelatorios() {
        System.out.println("\n── RELATÓRIOS ───────────────────────");
        System.out.println("1. Livros com Autores");
        System.out.println("2. Livros por Categoria");
        System.out.println("3. Clientes com Pedidos");
        System.out.print("Escolha: ");
        int op = lerInt();
        switch (op) {
            case 1 -> autorDAO.selectLivrosComAutores();
            case 2 -> categoriaDAO.selectLivrosComCategorias();
            case 3 -> clienteDAO.selectClientesComPedidos();
            default -> System.out.println("Opção inválida.");
        }
    }

    static void popularBanco() {
        System.out.println("\nPopulando banco de dados...");
        System.out.println("→ Inserindo categorias...");  categoriaDAO.adicionarCategorias();
        System.out.println("→ Inserindo autores...");     autorDAO.adicionarAutores();
        System.out.println("→ Inserindo livros...");      livroDAO.adicionarLivros();
        System.out.println("→ Inserindo clientes...");    clienteDAO.adicionarClientes();
        System.out.println("→ Inserindo pedidos...");     pedidoDAO.adicionarPedidos();
        System.out.println("✔ Banco populado com sucesso!");
    }

    static int lerInt() {
        int val = 0;
        try { val = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Valor inválido, usando 0."); }
        return val;
    }

    static float lerFloat() {
        float val = 0f;
        try { val = Float.parseFloat(sc.nextLine().trim().replace(",", ".")); }
        catch (NumberFormatException e) { System.out.println("Valor inválido, usando 0."); }
        return val;
    }
}
