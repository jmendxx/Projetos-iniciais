package Controller;

import DOA.FabricaConexao;
import Model.ModelFuncionarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionariosDAO {

    public static List<ModelFuncionarios> listarTodos() {
        List<ModelFuncionarios> lista = new ArrayList<>();
        String sql = "SELECT * FROM Funcionarios";

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelFuncionarios f = new ModelFuncionarios();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCpf(rs.getString("cpf"));
                f.setEndereco(rs.getString("endereco"));
                f.setTelefone(rs.getString("telefone"));
                f.setEmail(rs.getString("email"));
                f.setSexo(rs.getString("sexo"));
                f.setDataNasc(rs.getString("dataNascimento"));
                f.setFuncao(rs.getString("funcao"));
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ResultSet ListaFuncionarios() {
        String sql = "SELECT * FROM Funcionarios";
        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }
        return null;
    }

    public boolean inserirFuncionarios(ModelFuncionarios funcionario){
        String sql = "INSERT INTO Funcionarios (nome, cpf, telefone, email, endereco, sexo, dataNascimento, funcao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCpf());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getEmail());
            stmt.setString(5, funcionario.getEndereco());
            stmt.setString(6, funcionario.getSexo());
            stmt.setString(7, funcionario.getDataNasc());
            stmt.setString(8, funcionario.getFuncao());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFuncionarios(ModelFuncionarios fn) {
        String query = "UPDATE Funcionarios SET nome = ?, cpf = ?, telefone = ?, email = ?, endereco = ?, sexo = ?, dataNascimento = ?, funcao = ? WHERE id = ?";
        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, fn.getNome());
            pstmt.setString(2, fn.getCpf());
            pstmt.setString(3, fn.getTelefone());
            pstmt.setString(4, fn.getEmail());
            pstmt.setString(5, fn.getEndereco());
            pstmt.setString(6, fn.getSexo());
            pstmt.setString(7, fn.getDataNasc());
            pstmt.setString(8, fn.getFuncao());
            pstmt.setInt(9, fn.getId());
            int linhas = pstmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletaFuncionarios(ModelFuncionarios fn) {
        String query = "DELETE FROM Funcionarios WHERE id = ?";
        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, fn.getId());
            int linhasAfetadas = pstmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            // Imprime a exceção no console para entender o erro
            e.printStackTrace();
            return false;
        }
    }

}
