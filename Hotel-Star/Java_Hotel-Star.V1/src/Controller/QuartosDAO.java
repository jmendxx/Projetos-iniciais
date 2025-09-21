package Controller;

import DOA.FabricaConexao;
import Model.ModelQuartos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class QuartosDAO {

    public static List<ModelQuartos> listarTodos() {
        List<ModelQuartos> lista = new ArrayList<>();
        String sql = "SELECT id, numero, andar, tipo, valorDiaria, status FROM Quartos";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelQuartos q = new ModelQuartos();
                q.setId(rs.getInt("id"));
                q.setNum(rs.getInt("numero"));
                q.setAndar(rs.getString("andar"));
                q.setTipo(rs.getString("tipo"));
                q.setValorDiaria(rs.getDouble("valorDiaria"));
                q.setStatus(rs.getString("status"));
                lista.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void inserirQuartos(ModelQuartos quarto) {
        String sql = "INSERT INTO Quartos (numero, andar, tipo, valorDiaria) VALUES (?, ?, ?, ?)";
        System.out.println("Preparando para inserir quarto:");
        System.out.println("Número: " + quarto.getNum());


        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quarto.getNum());
            stmt.setString(2, quarto.getAndar());
            stmt.setString(3, quarto.getTipo());
            stmt.setDouble(4, quarto.getValorDiaria());

            int linhasAfetadas = stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir quarto: " + e.getMessage());
        }
    }


    public void updateQuartos(ModelQuartos Q) {
        // Definir a query SQL de atualização
        String query = "UPDATE Quartos SET numero = ?, andar = ?, tipo = ?,  WHERE id = ?";
        // Criar a conexão e preparar o statement
        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Definir os parâmetros do PreparedStatement
            pstmt.setInt(1, Q.getNum());
            pstmt.setString(2, Q.getAndar());
            pstmt.setString(3, Q.getTipo());
            pstmt.setInt(4, Q.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void deletaQuartos(ModelQuartos Q) {
        // Definir a query SQL de atualização
        String query = "DELETE FROM Quartos WHERE id = ?";
        // Criar a conexão e preparar o statement
        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, Q.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}