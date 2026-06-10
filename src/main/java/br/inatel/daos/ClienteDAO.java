package br.inatel.daos;

import br.inatel.models.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends ConnectionDAO {

    public boolean insertCliente(Cliente cliente) {
        connectToBd();
        String sql = "INSERT INTO Cliente (nome, cpf, email, telefone, data_nascimento) VALUES(?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getCpf());
            pst.setString(3, cliente.getEmail());
            pst.setString(4, cliente.getTelefone());
            pst.setString(5, cliente.getData_nascimento());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean updateCliente(String cpf, String novoEmail, String novoTelefone) {
        connectToBd();
        String sql = "UPDATE Cliente SET email=?, telefone=? WHERE cpf=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, novoEmail);
            pst.setString(2, novoTelefone);
            pst.setString(3, cpf);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deleteCliente(String cpf) {
        connectToBd();
        String sql = "DELETE FROM Cliente WHERE cpf=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, cpf);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public Cliente selectClientePorCpf(String cpf) {
        Cliente cliente = null;
        connectToBd();
        String sql = "SELECT * FROM Cliente WHERE cpf=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, cpf);
            rs = pst.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("data_nascimento")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao procurar cliente: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return cliente;
    }

    public List<Cliente> selectCliente() {
        List<Cliente> clientes = new ArrayList<>();
        connectToBd();
        String sql = "SELECT * FROM Cliente";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("telefone"),
                        rs.getString("data_nascimento")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return clientes;
    }

    // JOIN 3: Clientes com seus Pedidos
    public void selectClientesComPedidos() {
        connectToBd();
        String sql = "SELECT c.nome, c.email, p.id_pedido, p.status, p.total, p.observacao " +
                     "FROM Cliente c " +
                     "JOIN Pedido p ON c.id_cliente = p.id_cliente " +
                     "ORDER BY c.nome, p.id_pedido";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            System.out.println("\n=== Clientes com seus Pedidos ===");
            System.out.printf("%-35s %-10s %-12s %s%n", "Cliente", "Pedido Nº", "Status", "Total");
            System.out.println("-".repeat(75));
            while (rs.next()) {
                System.out.printf("%-35s %-10d %-12s R$%.2f%n",
                        rs.getString("nome"),
                        rs.getInt("id_pedido"),
                        rs.getString("status"),
                        rs.getFloat("total"));
            }
        } catch (SQLException e) {
            System.out.println("Erro no relatório Clientes-Pedidos: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
    }

    public void adicionarClientes() {
        String[][] dados = {
            {"Leonardo Fabricio Vieira Fernandes", "11122233344", "leonardo@email.com",  "(35) 99876-1111", "2003-04-15"},
            {"William Andrade Camilo",             "22233344455", "william@email.com",   "(35) 99876-2222", "2003-08-22"},
            {"Gabriel Amilton Perroni Camargo",    "33344455566", "gabriel@email.com",   "(35) 99876-3333", "2002-11-30"},
            {"Ana Carolina Souza",                 "44455566677", "ana.souza@email.com", "(35) 99876-4444", "1998-02-10"},
            {"Bruno Henrique Lima",                "55566677788", "bruno.lima@email.com","(35) 99876-5555", "1995-06-25"},
            {"Carla Mendes Oliveira",              "66677788899", "carla.m@email.com",   "(35) 99876-6666", "2000-01-12"}
        };
        for (String[] d : dados) insertCliente(new Cliente(d[0], d[1], d[2], d[3], d[4]));
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
