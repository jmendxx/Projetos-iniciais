package View;

import Controller.ProdutosDAO;
import DOA.FabricaConexao;
import Model.ModelFornecedores;
import Model.ModelProdutos;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class Produtos extends JFrame{
    private JPanel painelTabela;
    private JPanel painelBotoes;
    private JPanel painelTopo;
    private JPanel painelPrincipal;
    private JTable tbProdutos;
    private JButton bttNovo;
    private JButton bttEditar;
    private JButton bttExcluir;
    private JButton bttSalvar;
    private JButton bttFechar;
    private JTextField txtDescricao;
    private JTextField txtTamanho;
    private JTextField txtEstq;
    private JTextField txtFornecedor;
    private JTextField txtCnpj;
    private String perfil;
    private int idProd;

    public Produtos (String perfil) {
        this.perfil = perfil;
        setSize(1200,800);
        JScrollPane scrollPane = new JScrollPane(painelPrincipal, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        setContentPane(scrollPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        //****** BOTÃO NOVO ******
        bttNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                habilita();
                limpaCampos();
                txtDescricao.requestFocus();
            }
        });

        //****** BOTÃO EDITAR *******
        bttEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descricao = txtDescricao.getText().toString();
                String tamanho = txtTamanho.getText().toString();
                int estoque = Integer.parseInt(txtEstq.getText());
                String fornecedor = txtFornecedor.getText().toString();
                String cnpj = txtCnpj.getText().toString();


                ModelProdutos produto = new ModelProdutos();
                produto.setDescricao(descricao);
                produto.setTamanho(tamanho);
                produto.setEstoque(estoque);
                produto.setFornecedor(fornecedor);
                produto.setCnpj(cnpj);
                produto.setId(idProd);

                ModelFornecedores forn = new ModelFornecedores();
                forn.setRegSocial(fornecedor);
                forn.setCnpj(cnpj);

                ProdutosDAO prodDAO = new ProdutosDAO();
                prodDAO.updateProdutos(produto);
                carregarDados();

            }
        });

        //****** BOTÃO DELETAR *******
        bttExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacao = JOptionPane.showConfirmDialog(
                        null, "Tem certeza que deseja excluir este usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
                ModelProdutos produto = new ModelProdutos();
                if (confirmacao == JOptionPane.YES_OPTION) {

                    produto.setId(idProd);
                }
                ProdutosDAO prodDAO = new ProdutosDAO();
                prodDAO.deletaProdutos(produto);

                carregarDados();
                limpaCampos();
            }
        });

        //****** BOTAO SALVAR *******
        bttSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String descricao = txtDescricao.getText();
                    String tamanho = txtTamanho.getText();
                    int estoque = Integer.parseInt(txtEstq.getText());
                    String fornecedor = txtFornecedor.getText();
                    String cnpj = txtCnpj.getText();

                    ModelProdutos produto = new ModelProdutos();
                    produto.setDescricao(descricao);
                    produto.setTamanho(tamanho);
                    produto.setEstoque(estoque);
                    produto.setFornecedor(fornecedor);
                    produto.setCnpj(cnpj);

                    ProdutosDAO prodDAO = new ProdutosDAO();

                    boolean inserido = prodDAO.inserirProdutos(produto);

                    if (inserido) {
                        JOptionPane.showMessageDialog(null, "Informações salvas com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao salvar informações.");
                    }


                    limpaCampos();
                    carregarDados();
                    desabilita();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Estoque deve ser um número válido!");
                }
            }
        });


        //****** BOTÃO VOLTAR PARA TELA INICIAL (FECHAR) ******
        bttFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Home(perfil);
                dispose();
            }
        });

        //****** CLIQUE NA LINHA DA TABELA *******
        tbProdutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int linhaSelecionada = tbProdutos.getSelectedRow();
                    if (linhaSelecionada != -1) {
                        habilita();
                        idProd = (int) tbProdutos.getValueAt(linhaSelecionada, 0);
                        txtDescricao.setText(tbProdutos.getValueAt(linhaSelecionada, 1).toString());
                        txtTamanho.setText(tbProdutos.getValueAt(linhaSelecionada, 2).toString());
                        txtEstq.setText(tbProdutos.getValueAt(linhaSelecionada, 3).toString());
                        txtFornecedor.setText(tbProdutos.getValueAt(linhaSelecionada, 4).toString());
                        txtCnpj.setText(tbProdutos.getValueAt(linhaSelecionada, 5).toString());

                    }
                }

            }
        });

        carregarDados();
        desabilita();
    }

    public void limpaCampos() {
        txtDescricao.setText("");
        txtTamanho.setText("");
        txtEstq.setText("");
        txtFornecedor.setText("");
        txtCnpj.setText("");
    }
    public void desabilita() {
        txtDescricao.setEnabled(false);
        txtTamanho.setEnabled(false);
        txtEstq.setEnabled(false);
        txtFornecedor.setEnabled(false);
        txtCnpj.setEnabled(false);
        bttEditar.setEnabled(false);
        bttExcluir.setEnabled(false);
        bttSalvar.setEnabled(false);
    }

    public void habilita() {
        txtDescricao.setEnabled(true);
        txtTamanho.setEnabled(true);
        txtEstq.setEnabled(true);
        txtFornecedor.setEnabled(true);
        txtCnpj.setEnabled(true);
        bttEditar.setEnabled(true);
        bttExcluir.setEnabled(true);
        bttSalvar.setEnabled(true);
        bttNovo.setEnabled(true);
    }

    public void carregarDados() {
        try (Connection conn = FabricaConexao.conectar()) {
            String query = "SELECT id, descricao, tamanho, estoque, fornecedor, cnpj FROM Produtos";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Vector<String> colunas = new Vector<>();
            colunas.add("Id");
            colunas.add("Descrição");
            colunas.add("Tamanho");
            colunas.add("Estoque");
            colunas.add("Fornecedor");
            colunas.add("CNPJ");

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getInt("id"));
                linha.add(rs.getString("descricao"));
                linha.add(rs.getString("tamanho"));
                linha.add(rs.getInt("estoque"));
                linha.add(rs.getString("fornecedor"));
                linha.add(rs.getString("cnpj"));
                dados.add(linha);
            }

            tbProdutos.setModel(new DefaultTableModel(dados, colunas));
            tbProdutos.setRowHeight(30);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
