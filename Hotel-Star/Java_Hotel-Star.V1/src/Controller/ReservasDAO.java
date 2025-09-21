package Controller;

import DOA.FabricaConexao;
import Model.ModelHospedes;
import Model.ModelQuartos;
import Model.ModelReservas;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservasDAO {

    public void inserirReserva(ModelReservas reserva) {
        String sql = "INSERT INTO Reservas (idHospede, dataCheckin, dataCheckout, diaria, valorTotal, idQuarto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getIdHospede());
            stmt.setDate(2, Date.valueOf(reserva.getCheckIn()));
            stmt.setDate(3, Date.valueOf(reserva.getCheckOut()));
            stmt.setInt(4, reserva.getDiaria());
            stmt.setDouble(5, reserva.getValorDiaria());
            stmt.setDouble(6, reserva.getValorTotal());
            stmt.setInt(7, reserva.getIdQuarto());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void atualizarReserva(ModelReservas reserva) {
        String sql = "UPDATE Reservas SET idHospede = ?, dataCheckin = ?, dataCheckout = ?, diaria = ?, valorTotal = ?, idQuarto = ? WHERE id = ?";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reserva.getIdHospede());
            stmt.setDate(2, Date.valueOf(reserva.getCheckIn()));
            stmt.setDate(3, Date.valueOf(reserva.getCheckOut()));
            stmt.setInt(4, reserva.getDiaria());
            stmt.setDouble(5, reserva.getValorTotal());
            stmt.setInt(6, reserva.getIdQuarto());
            stmt.setInt(7, reserva.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ModelReservas> listarTodos() {
        List<ModelReservas> lista = new ArrayList<>();

        String sql = "SELECT r.id, h.id AS idHospede, h.nome AS nomeHospede, h.cpf AS cpfHospede, " +
                "r.dataCheckin, r.dataCheckout, q.id AS idQuarto, q.numero AS numeroQuarto, " +
                "q.tipo AS tipoQuarto, r.diaria, q.valorDiaria AS valorDiaria, r.valorTotal " +
                "FROM Reservas r " +
                "JOIN Hospedes h ON r.idHospede = h.id " +
                "JOIN Quartos q ON r.idQuarto = q.id";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelReservas reserva = new ModelReservas();
                reserva.setId(rs.getInt("id"));
                reserva.setCheckIn(rs.getString("dataCheckin"));
                reserva.setCheckOut(rs.getString("dataCheckout"));
                reserva.setDiaria(rs.getInt("diaria"));
                reserva.setValorDiaria(rs.getDouble("valorDiaria"));
                reserva.setValorTotal(rs.getDouble("valorTotal"));

                ModelHospedes hospede = new ModelHospedes();
                hospede.setId(rs.getInt("idHospede"));
                hospede.setNome(rs.getString("nomeHospede"));
                hospede.setCpf(rs.getString("cpfHospede"));
                reserva.setIdHospede(hospede.getId());

                ModelQuartos quarto = new ModelQuartos();
                quarto.setId(rs.getInt("idQuarto"));
                quarto.setNum(rs.getInt("numeroQuarto"));
                quarto.setTipo(rs.getString("tipoQuarto"));
                reserva.setIdQuarto(quarto.getId());

                lista.add(reserva);
            }

        } catch (SQLException e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao listar reservas: " + e.getMessage());
        }
        return lista;
    }



}

