package br.inatel.daos;

import br.inatel.models.Pedido;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO extends ConnectionDAO {

    public boolean insertPedido(Pedido pedido) {
        connectToBd();
        String sql = "INSERT INTO Pedido (id_cliente, status, total, observacao) VALUES(?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, pedido.getId_cliente());
            pst.setString(2, pedido.getStatus());
            pst.setFloat(3, pedido.getTotal());
            pst.setString(4, pedido.getObservacao());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir pedido: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean updatePedido(Pedido pedido) {
        connectToBd();
        String sql = "UPDATE Pedido SET status=?, total=?, observacao=? WHERE id_cliente=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, pedido.getStatus());
            pst.setFloat(2, pedido.getTotal());
            pst.setString(3, pedido.getObservacao());
            pst.setInt(4, pedido.getId_cliente());
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pedido: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public boolean deletePedido(int id_pedido) {
        connectToBd();
        String sql = "DELETE FROM Pedido WHERE id_pedido=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id_pedido);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar pedido: " + e.getMessage());
            return false;
        } finally {
            fecharRecursos();
        }
    }

    public Pedido selectPedido(int id_pedido) {
        Pedido pedido = null;
        connectToBd();
        String sql = "SELECT id_cliente, status, total, observacao FROM Pedido WHERE id_pedido=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id_pedido);
            rs = pst.executeQuery();
            if (rs.next()) {
                pedido = new Pedido(
                        rs.getInt("id_cliente"),
                        rs.getString("status"),
                        rs.getString("observacao"),
                        rs.getFloat("total")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao procurar pedido: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return pedido;
    }

    public List<Pedido> selectTodosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        connectToBd();
        String sql = "SELECT id_cliente, status, total, observacao FROM Pedido";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                pedidos.add(new Pedido(
                        rs.getInt("id_cliente"),
                        rs.getString("status"),
                        rs.getString("observacao"),
                        rs.getFloat("total")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
        } finally {
            fecharRecursos();
        }
        return pedidos;
    }

    public void adicionarPedidos() {
        Object[][] dados = {
            {1, "entregue",  85.40f,  "Entregar em horario comercial."},
            {2, "pago",     134.80f,  "Cliente pediu embalagem para presente."},
            {3, "enviado",  121.40f,  "Frete expresso solicitado."},
            {4, "pendente",  45.00f,  "Aguardando confirmacao de pagamento."},
            {5, "cancelado", 89.90f,  "Cancelado pelo cliente."},
            {1, "pago",      69.40f,  "Segundo pedido do cliente."}
        };
        for (Object[] d : dados)
            insertPedido(new Pedido((int)d[0], (String)d[1], (String)d[3], (float)d[2]));
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
