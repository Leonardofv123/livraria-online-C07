package br.inatel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO extends ConnectionDAO {

    public boolean insertCategoria(Categoria categoria) {
        connectToBd();
        String sql = "INSERT INTO Categoria (nome, descricao) VALUES(?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, categoria.getNome());
            pst.setString(2, categoria.getDescricao());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir categoria: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean updateCategoria(String nome, String novaDescricao) {
        connectToBd();
        String sql = "UPDATE Categoria SET descricao=? WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, novaDescricao);
            pst.setString(2, nome);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar categoria: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteCategoria(String nome) {
        connectToBd();
        String sql = "DELETE FROM Categoria WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar categoria: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public Categoria selectCategoria(String nome) {
        Categoria categoria = null;
        connectToBd();
        String sql = "SELECT * FROM Categoria WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                categoria = new Categoria(rs.getString("nome"), rs.getString("descricao"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao procurar categoria: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return categoria;
    }

    public List<Categoria> selectCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        connectToBd();
        String sql = "SELECT * FROM Categoria";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                categorias.add(new Categoria(rs.getString("nome"), rs.getString("descricao")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar categorias: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return categorias;
    }

    // JOIN 2: Livros por Categoria (usa id_livro / id_categoria conforme o banco)
    public void selectLivrosComCategorias() {
        connectToBd();
        String sql = "SELECT l.titulo, l.preco, l.estoque, c.nome AS categoria " +
                     "FROM Livro l " +
                     "JOIN Livro_Categoria lc ON l.id_livro = lc.id_livro " +
                     "JOIN Categoria c ON lc.id_categoria = c.id_categoria " +
                     "ORDER BY c.nome, l.titulo";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            System.out.println("\n=== Livros por Categoria ===");
            System.out.printf("%-35s %-25s %-8s %s%n", "Título", "Categoria", "Estoque", "Preço");
            System.out.println("-".repeat(80));
            while (rs.next()) {
                System.out.printf("%-35s %-25s %-8d R$%.2f%n",
                        rs.getString("titulo"),
                        rs.getString("categoria"),
                        rs.getInt("estoque"),
                        rs.getFloat("preco"));
            }
        } catch (SQLException e) {
            System.out.println("Erro no relatório Livros-Categorias: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
    }

    public void adicionarCategorias() {
        // Dados idênticos ao INSERT do script SQL
        String[][] dados = {
            {"Romance",               "Obras de ficcao narrativa com foco em relacionamentos e desenvolvimento de personagens."},
            {"Literatura Brasileira", "Obras escritas por autores brasileiros que retratam aspectos culturais e historicos do Brasil."},
            {"Ficcao Cientifica",     "Genero que explora temas como tecnologia, futuro, espaco e realidades alternativas."},
            {"Fantasia",              "Obras com elementos magicos, mitologia e mundos imaginarios."},
            {"Distopia",              "Narrativas ambientadas em sociedades opressivas ou em colapso."},
            {"Realismo Magico",       "Genero literario que mistura elementos fantasticos em contextos realistas."}
        };
        for (String[] d : dados) {
            insertCategoria(new Categoria(d[0], d[1]));
        }
    }

    private void fecharRecursos() {
        try {
            if (pst != null) pst.close();
            if (st  != null) st.close();
            if (rs  != null) rs.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }
}
