package Controller;

import DOA.FabricaConexao;
import Model.ModelProdutos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {

    public ResultSet ListaHospedes() {
        String sql = "SELECT * FROM Produtos";
        try {
            Connection conn = FabricaConexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao validar login: " + e.getMessage());
        }
        return null;
    }

    public boolean inserirProdutos(ModelProdutos produtos) {
        String sql = "INSERT INTO Produtos (descricao, tamanho, estoque, fornecedor, cnpj) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produtos.getDescricao());
            stmt.setString(2, produtos.getTamanho());
            stmt.setInt(3, produtos.getEstoque());
            stmt.setString(4, produtos.getFornecedor());
            stmt.setString(5, produtos.getCnpj());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void updateProdutos(ModelProdutos p) {
        // Definir a query SQL de atualização
        String query = "UPDATE Produtos SET descricao = ?,tamanho = ?, estoque = ?, " +
                "fornecedor = ?, cnpj = ? WHERE id = ?";
        // Criar a conexão e preparar o statement
        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Definir os parâmetros do PreparedStatement
            pstmt.setString(1, p.getDescricao());
            pstmt.setString(2, p.getTamanho());
            pstmt.setInt(3, p.getEstoque());
            pstmt.setString(4, p.getFornecedor());
            pstmt.setString(5, p.getCnpj());
            pstmt.setInt(6, p.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void deletaProdutos(ModelProdutos p) {
        // Definir a query SQL de atualização
        String query = "DELETE FROM Produtos  WHERE id = ?";
        // Criar a conexão e preparar o statement
        try (Connection connection = FabricaConexao.conectar();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, p.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<ModelProdutos> listarTodos() {
        List<ModelProdutos> lista = new ArrayList<>();
        String sql = "SELECT * FROM Produtos"; // ajuste conforme o nome da sua tabela

        try (Connection conn = FabricaConexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ModelProdutos p = new ModelProdutos();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setTamanho(rs.getString("tamanho"));
                p.setEstoque(rs.getInt("estoque"));
                p.setFornecedor(rs.getString("fornecedor"));
                p.setCnpj(rs.getString("cnpj"));
                // adicione outros campos do model conforme necessário
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
