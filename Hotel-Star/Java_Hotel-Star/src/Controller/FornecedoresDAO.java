package Controller;

import DOA.FabricaConexao;
import Model.ModelFornecedores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FornecedoresDAO {

    public boolean inserirFornecedores(ModelFornecedores fornecedores) {
        String sql = "INSERT INTO Fornecedores (regSocial, cnpj, endereco, telefone, email, represLegal, cpf) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fornecedores.getRegSocial());
            stmt.setString(2, fornecedores.getCnpj());
            stmt.setString(3, fornecedores.getEndereco());
            stmt.setString(4, fornecedores.getTelefone());
            stmt.setString(5, fornecedores.getEmail());
            stmt.setString(6, fornecedores.getRepresLegal());
            stmt.setString(7, fornecedores.getCpf());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFornecedores(ModelFornecedores f) {
        String query = "UPDATE Fornecedores SET regSocial = ?, cnpj = ?, endereco = ?, " +
                "telefone = ?, email = ?, represLegal = ?, cpf = ? WHERE id = ?";

        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, f.getRegSocial());
            pstmt.setString(2, f.getCnpj());
            pstmt.setString(3, f.getEndereco());
            pstmt.setString(4, f.getTelefone());
            pstmt.setString(5, f.getEmail());
            pstmt.setString(6, f.getRepresLegal());
            pstmt.setString(7, f.getCpf());
            pstmt.setInt(8, f.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletaFornecedores(ModelFornecedores f) {
        String query = "DELETE FROM Fornecedores WHERE id = ?";

        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, f.getId());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<ModelFornecedores> listarTodos() {
        List<ModelFornecedores> lista = new ArrayList<>();
        String sql = "SELECT * FROM Fornecedores";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelFornecedores f = new ModelFornecedores();
                f.setId(rs.getInt("id"));
                f.setRegSocial(rs.getString("regSocial"));
                f.setCnpj(rs.getString("cnpj"));
                f.setEndereco(rs.getString("endereco"));
                f.setTelefone(rs.getString("telefone"));
                f.setEmail(rs.getString("email"));
                f.setRepresLegal(rs.getString("represLegal"));
                f.setCpf(rs.getString("cpf"));
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

