package Controller;

import Model.ModelHospedes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static DOA.FabricaConexao.conectar;


public class HospedesDAO {

    public ResultSet ListaHospedes() {
        String sql = "SELECT * FROM Hospedes";
        try {
            Connection conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao validar login: " + e.getMessage());
        }
        return null;
    }

    public boolean inserirHospedes(ModelHospedes hospedes) {
        String sql = "INSERT INTO Hospedes (nome, cpf, dataNascimento, telefone, email, endereco, sexo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hospedes.getNome());
            stmt.setString(2, hospedes.getCpf());
            stmt.setString(3, hospedes.getDataNasc());
            stmt.setString(4, hospedes.getTelefone());
            stmt.setString(5, hospedes.getEmail());
            stmt.setString(6, hospedes.getEndereco());
            stmt.setString(7, hospedes.getSexo());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();  // Mostra erro no console
            return false;
        }
    }


    public void updateHospedes(ModelHospedes h) {
        // Definir a query SQL de atualização
        String query = "UPDATE Hospedes SET nome = ?,cpf = ?, dataNascimento = ?, " +
                "telefone = ?, email = ?, endereco = ?, sexo = ? WHERE id = ?";

        // Criar a conexão e preparar o statement
        try (Connection connection = conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Definir os parâmetros do PreparedStatement
            pstmt.setString(1, h.getNome());
            pstmt.setString(2, h.getCpf());
            pstmt.setString(3, h.getEndereco());
            pstmt.setString(4, h.getTelefone());
            pstmt.setString(5, h.getEmail());
            pstmt.setString(6, h.getSexo());
            pstmt.setString(7, h.getDataNasc());
            pstmt.setInt(8, h.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void deletaHospedes(ModelHospedes h) {
        // Definir a query SQL de atualização
        String query = "DELETE FROM Hospedes  WHERE id = ?";
        // Criar a conexão e preparar o statement
        try (Connection connection = conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, h.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<ModelHospedes> listarTodos() {
        List<ModelHospedes> lista = new ArrayList<>();
        String sql = "SELECT * FROM Hospedes";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelHospedes h = new ModelHospedes();
                h.setId(rs.getInt("id"));
                h.setNome(rs.getString("nome"));
                h.setCpf(rs.getString("cpf"));
                h.setDataNasc(rs.getString("dataNascimento"));
                h.setTelefone(rs.getString("telefone"));
                h.setEmail(rs.getString("email"));
                h.setEndereco(rs.getString("endereco"));
                h.setSexo(rs.getString("sexo"));

                // adicione outros campos conforme seu model
                lista.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
