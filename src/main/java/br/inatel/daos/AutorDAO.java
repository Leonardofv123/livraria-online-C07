package br.inatel.daos;

import br.inatel.models.Autor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO extends ConnectionDAO {

    public boolean insertAutor(Autor autor) {
        connectToBd();
        String sql = "INSERT INTO Autor (nome, nacionalidade, data_nascimento, biografia) VALUES(?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, autor.getNome());
            pst.setString(2, autor.getNacionalidade());
            pst.setString(3, autor.getData_nascimento());
            pst.setString(4, autor.getBiografia());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir autor: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean updateAutor(String nome, String novaNacionalidade, String novaBiografia) {
        connectToBd();
        String sql = "UPDATE Autor SET nacionalidade=?, biografia=? WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, novaNacionalidade);
            pst.setString(2, novaBiografia);
            pst.setString(3, nome);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar autor: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteAutor(String nome) {
        connectToBd();
        String sql = "DELETE FROM Autor WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar autor: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public Autor selectAutor(String nome) {
        Autor autor = null;
        connectToBd();
        String sql = "SELECT * FROM Autor WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                autor = new Autor(
                        rs.getString("nome"),
                        rs.getString("nacionalidade"),
                        rs.getString("data_nascimento"),
                        rs.getString("biografia")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao procurar autor: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return autor;
    }

    public List<Autor> selectTodosAutores() {
        List<Autor> autores = new ArrayList<>();
        connectToBd();
        String sql = "SELECT * FROM Autor";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                autores.add(new Autor(
                        rs.getString("nome"),
                        rs.getString("nacionalidade"),
                        rs.getString("data_nascimento"),
                        rs.getString("biografia")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar autores: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return autores;
    }

    // JOIN 1: Livros com seus Autores
    public void selectLivrosComAutores() {
        connectToBd();
        String sql = "SELECT l.titulo, l.preco, l.ano_publicacao, a.nome AS autor, a.nacionalidade " +
                     "FROM Livro l " +
                     "JOIN Autor_Livro al ON l.id_livro = al.id_livro " +
                     "JOIN Autor a ON al.id_autor = a.id_autor " +
                     "ORDER BY l.titulo";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            System.out.println("\n=== Livros com seus Autores ===");
            System.out.printf("%-35s %-25s %-6s %s%n", "Título", "Autor", "Ano", "Preço");
            System.out.println("-".repeat(80));
            while (rs.next()) {
                System.out.printf("%-35s %-25s %-6d R$%.2f%n",
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("ano_publicacao"),
                        rs.getFloat("preco"));
            }
        } catch (SQLException e) {
            System.out.println("Erro no relatório Livros-Autores: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
    }

    public void adicionarAutores() {
        String[][] dados = {
            {"Machado de Assis",       "Brasileira", "1839-06-21", "Considerado o maior escritor da literatura brasileira, fundador da Academia Brasileira de Letras."},
            {"Clarice Lispector",      "Brasileira", "1920-12-10", "Romancista e contista, uma das principais escritoras brasileiras do seculo XX."},
            {"George Orwell",          "Britanica",  "1903-06-25", "Jornalista e escritor britanico, conhecido por obras de critica politica e social."},
            {"J. R. R. Tolkien",       "Britanica",  "1892-01-03", "Filologo e escritor, criador do universo da Terra Media."},
            {"Gabriel Garcia Marquez", "Colombiana", "1927-03-06", "Escritor colombiano, premio Nobel de Literatura em 1982, expoente do realismo magico."},
            {"Jorge Amado",            "Brasileira", "1912-08-10", "Romancista baiano, um dos autores brasileiros mais traduzidos no mundo."}
        };
        for (String[] d : dados) insertAutor(new Autor(d[0], d[1], d[2], d[3]));
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
