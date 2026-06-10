package br.inatel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO extends ConnectionDAO {

    public boolean insertLivro(Livro livro) {
        connectToBd();
        String sql = "INSERT INTO Livro (titulo, isbn, preco, estoque, ano_publicacao, descricao) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, livro.getTitulo());
            pst.setString(2, livro.getIsbn());
            pst.setFloat(3, livro.getPreco());
            pst.setInt(4, livro.getEstoque());
            pst.setInt(5, livro.getAno_publicacao());
            pst.setString(6, livro.getDescricao());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir livro: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean updateLivro(Livro livro) {
        connectToBd();
        String sql = "UPDATE Livro SET preco=?, estoque=? WHERE titulo=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setFloat(1, livro.getPreco());
            pst.setInt(2, livro.getEstoque());
            pst.setString(3, livro.getTitulo());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar livro: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteLivro(String titulo) {
        connectToBd();
        String sql = "DELETE FROM Livro WHERE titulo=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, titulo);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar livro: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public Livro selectLivroPorTitulo(String titulo) {
        Livro livro = null;
        connectToBd();
        String sql = "SELECT * FROM Livro WHERE titulo=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, titulo);
            rs = pst.executeQuery();
            if (rs.next()) {
                livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getString("descricao"),
                        rs.getInt("estoque"),
                        rs.getInt("ano_publicacao"),
                        rs.getFloat("preco")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao procurar livro: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return livro;
    }

    public float selectPreco(String titulo) {
        float preco = 0;
        connectToBd();
        String sql = "SELECT preco FROM Livro WHERE titulo=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, titulo);
            rs = pst.executeQuery();
            if (rs.next()) preco = rs.getFloat("preco");
        } catch (SQLException e) {
            System.out.println("Erro ao buscar preço: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return preco;
    }

    public List<Livro> selectLivro() {
        List<Livro> livros = new ArrayList<>();
        connectToBd();
        String sql = "SELECT * FROM Livro";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                livros.add(new Livro(
                        rs.getString("titulo"),
                        rs.getString("isbn"),
                        rs.getString("descricao"),
                        rs.getInt("estoque"),
                        rs.getInt("ano_publicacao"),
                        rs.getFloat("preco")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return livros;
    }

    public void adicionarLivros() {
        // Dados idênticos ao INSERT do script SQL
        Object[][] dados = {
            {"Dom Casmurro",           "9788535910663", 39.90f, 25, 1899, "Classico de Machado de Assis sobre ciume e ambiguidade narrativa."},
            {"A Hora da Estrela",      "9788532530803", 32.50f, 18, 1977, "Ultima obra de Clarice Lispector, sobre Macabea, uma jovem nordestina no Rio."},
            {"1984",                   "9788535914849", 45.00f, 30, 1949, "Romance distopico de George Orwell sobre um regime totalitario."},
            {"O Senhor dos Aneis",     "9788578277109", 89.90f, 15, 1954, "Epico de fantasia de Tolkien ambientado na Terra Media."},
            {"Cem Anos de Solidao",    "9788501012074", 54.90f, 22, 1967, "Obra-prima de Garcia Marquez, marco do realismo magico."},
            {"Capitaes da Areia",      "9788535914207", 36.50f, 20, 1937, "Romance de Jorge Amado sobre meninos de rua em Salvador."},
            {"A Revolucao dos Bichos", "9788535909555", 28.90f, 40, 1945, "Fabula politica de George Orwell."}
        };
        for (Object[] d : dados) {
            insertLivro(new Livro((String)d[0], (String)d[1], (String)d[5], (int)d[3], (int)d[4], (float)d[2]));
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
