package Controller;

import DOA.FabricaConexao;
import Model.ModelUsuarios;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static DOA.FabricaConexao.conectar;

public class UsuariosDAO {

    public ResultSet ListaUsuarios() {
        String sql = "SELECT u.usuario, u.senha, u.perfil, f.nome, f.cpf " +
                "FROM Usuarios u, Funcionarios f " +
                "WHERE u.id_funcionario = f.id_funcionario";
        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao validar login: " + e.getMessage());
        }
        return null;
    }

    public boolean inserirUsuarios(ModelUsuarios usuario) {
        String sql = "INSERT INTO Usuarios (usuario, senha, perfil, id_funcionario) VALUES (?, ?, ?, ?)";
        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUser());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getPerfil());
            stmt.setInt(4, usuario.getIdFunc());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;  // Retorna true se pelo menos 1 linha foi inserida

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateUsuarios(ModelUsuarios u) {

        // Definir a query SQL de atualização
        String query = "UPDATE Usuarios SET usuario = ?, senha = ?, perfil = ?, id_funcionario = ? WHERE id = ?";

        // Criar a conexão e preparar o statement
        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            // Definir os parâmetros do PreparedStatement
            pstmt.setString(1, u.getUser());
            pstmt.setString(2, u.getSenha());
            pstmt.setString(3, u.getPerfil());
            pstmt.setInt(4, u.getIdFunc());
            pstmt.setInt(5, u.getId());

            pstmt.executeUpdate();


        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    public boolean deletaUsuarios(ModelUsuarios u) {
        String query = "DELETE FROM Usuarios WHERE id = ?";
        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, u.getId());
            int linhasAfetadas = pstmt.executeUpdate();

            return linhasAfetadas > 0;  // true se deletou pelo menos 1 linha

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<ModelUsuarios> listarTodos() {
        List<ModelUsuarios> lista = new ArrayList<>();
        String sql = "SELECT u.id, u.usuario, u.senha, u.perfil, u.id_funcionario, f.nome AS nomeFuncionario " +
                "FROM Usuarios u " +
                "LEFT JOIN Funcionarios f ON u.id_funcionario = f.id";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelUsuarios usuario = new ModelUsuarios();
                usuario.setId(rs.getInt("id"));
                usuario.setUser(rs.getString("usuario"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil(rs.getString("perfil"));
                usuario.setIdFunc(rs.getInt("id_funcionario"));


                lista.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
