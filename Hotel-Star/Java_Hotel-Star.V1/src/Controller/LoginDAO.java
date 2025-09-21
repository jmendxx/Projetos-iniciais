package Controller;
import DOA.FabricaConexao;
import Model.ModelUsuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {

    public ModelUsuarios validarLogin(String usuario, String senha, String perfil){
        int result = 0;
        ModelUsuarios levaDados;
        levaDados = null;
        String sql = "SELECT* FROM Usuarios WHERE usuario = ? AND senha = ? AND perfil = ?";
        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,usuario);
            stmt.setString(2,senha);
            stmt.setString(3,perfil);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                levaDados = new ModelUsuarios();
                levaDados.setUser(rs.getNString("usuario"));
                levaDados.setSenha(rs.getNString("senha"));
                levaDados.setPerfil(rs.getNString("perfil"));
            }


        } catch (SQLException e){
            System.out.println("Erro ao validar login: " + e.getMessage());
        }
        return levaDados;
    }

}
