package View;

import Controller.FornecedoresDAO;
import DOA.FabricaConexao;
import Model.ModelFornecedores;

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


public class Fornecedores extends JFrame{
    private JPanel painelPrincipal;
    private JPanel painelTopo;
    private JPanel painelBotoes;
    private JPanel PainelTabela;
    private JTable tbFornecedores;
    private JButton bttNovo;
    private JButton bttEditar;
    private JButton bttExcluir;
    private JButton bttSalvar;
    private JButton bttFechar;
    private int idForn; // ID Fornecedor
    private JTextField txtRLegal; //Representante Legal
    private JTextField txtRSocial; //Registro Social
    private JTextField txtCnpj;
    private JTextField txtEmail;
    private JTextField txtTel;
    private JTextField txtEnd;
    private JTextField txtCpf;

    private String perfil;

    public Fornecedores(String perfil) {
        this.perfil = perfil;

        setSize(1200,800);
        JScrollPane scrollPane = new JScrollPane(painelPrincipal, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        setContentPane(scrollPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        carregarDados();

        //****** BOTÃO NOVO ******
        bttNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habilita();
                limpaCampos();
                txtRLegal.requestFocus();
            }
        });

        //****** BOTÃO EDITAR *******
        bttEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelFornecedores fornecedor = new ModelFornecedores();
                fornecedor.setId(idForn);
                fornecedor.setRegSocial(txtRSocial.getText());
                fornecedor.setCnpj(txtCnpj.getText());
                fornecedor.setTelefone(txtTel.getText());
                fornecedor.setEndereco(txtEnd.getText());
                fornecedor.setEmail(txtEmail.getText());
                fornecedor.setRepresLegal(txtRLegal.getText());
                fornecedor.setCpf(txtCpf.getText());

                FornecedoresDAO fornDAO = new FornecedoresDAO();
                boolean atualizado = fornDAO.updateFornecedores(fornecedor);
                if (atualizado) {
                    JOptionPane.showMessageDialog(Fornecedores.this, "Fornecedor atualizado com sucesso!");
                    carregarDados();
                    limpaCampos();
                    desabilita();
                } else {
                    JOptionPane.showMessageDialog(Fornecedores.this, "Erro ao atualizar fornecedor.");
                }
            }
        });

        //****** BOTÃO DELETAR *******
        bttExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacao = JOptionPane.showConfirmDialog(
                        Fornecedores.this,
                        "Tem certeza que deseja excluir este fornecedor?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmacao == JOptionPane.YES_OPTION) {
                    ModelFornecedores fornecedor = new ModelFornecedores();
                    fornecedor.setId(idForn);

                    FornecedoresDAO fornDAO = new FornecedoresDAO();
                    boolean excluido = fornDAO.deletaFornecedores(fornecedor);
                    if (excluido) {
                        JOptionPane.showMessageDialog(Fornecedores.this, "Fornecedor excluído com sucesso!");
                        carregarDados();
                        limpaCampos();
                        desabilita();
                    } else {
                        JOptionPane.showMessageDialog(Fornecedores.this, "Erro ao excluir fornecedor.");
                    }
                }
            }
        });

        //****** BOTAO SALVAR *******
        bttSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelFornecedores fornecedor = new ModelFornecedores();
                fornecedor.setRegSocial(txtRSocial.getText());
                fornecedor.setCnpj(txtCnpj.getText());
                fornecedor.setTelefone(txtTel.getText());
                fornecedor.setEndereco(txtEnd.getText());
                fornecedor.setEmail(txtEmail.getText());
                fornecedor.setRepresLegal(txtRLegal.getText());
                fornecedor.setCpf(txtCpf.getText());

                FornecedoresDAO fornecDAO = new FornecedoresDAO();
                boolean inserido = fornecDAO.inserirFornecedores(fornecedor);

                if (inserido) {
                    JOptionPane.showMessageDialog(Fornecedores.this, "Fornecedor salvo com sucesso!");
                    carregarDados();
                    limpaCampos();
                    desabilita();
                } else {
                    JOptionPane.showMessageDialog(Fornecedores.this, "Erro ao salvar fornecedor.");
                }
            }
        });

        bttFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Home(perfil);
                dispose();
            }
        });

        //****** CLIQUE NA LINHA DA TABELA *******
    tbFornecedores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int linhaSelecionada = tbFornecedores.getSelectedRow();
                if (linhaSelecionada != -1) {
                    habilita();
                    try {
                        idForn = Integer.parseInt(tbFornecedores.getValueAt(linhaSelecionada, 0).toString());
                        txtRSocial.setText(tbFornecedores.getValueAt(linhaSelecionada, 1).toString());
                        txtCnpj.setText(tbFornecedores.getValueAt(linhaSelecionada, 2).toString());
                        txtTel.setText(tbFornecedores.getValueAt(linhaSelecionada, 3).toString());
                        txtEmail.setText(tbFornecedores.getValueAt(linhaSelecionada, 4).toString());
                        txtEnd.setText(tbFornecedores.getValueAt(linhaSelecionada, 5).toString());
                        txtRLegal.setText(tbFornecedores.getValueAt(linhaSelecionada, 6).toString());
                        txtCpf.setText(tbFornecedores.getValueAt(linhaSelecionada, 7).toString());

                        bttEditar.setEnabled(true);
                        bttExcluir.setEnabled(true);
                        bttSalvar.setEnabled(false);
                        bttNovo.setEnabled(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    });
}

public void limpaCampos() {
    txtRSocial.setText("");
    txtCnpj.setText("");
    txtTel.setText("");
    txtEnd.setText("");
    txtEmail.setText("");
    txtRLegal.setText("");
    txtCpf.setText("");
    idForn = 0;
}

public void desabilita() {
    txtRSocial.setEnabled(false);
    txtCnpj.setEnabled(false);
    txtTel.setEnabled(false);
    txtEnd.setEnabled(false);
    txtEmail.setEnabled(false);
    txtRLegal.setEnabled(false);
    txtCpf.setEnabled(false);
    bttEditar.setEnabled(false);
    bttExcluir.setEnabled(false);
    bttSalvar.setEnabled(false);
    bttNovo.setEnabled(true);
}

public void habilita() {
    txtRSocial.setEnabled(true);
    txtCnpj.setEnabled(true);
    txtTel.setEnabled(true);
    txtEnd.setEnabled(true);
    txtEmail.setEnabled(true);
    txtRLegal.setEnabled(true);
    txtCpf.setEnabled(true);
    bttEditar.setEnabled(false);
    bttExcluir.setEnabled(false);
    bttSalvar.setEnabled(true);
    bttNovo.setEnabled(false);
}


public void carregarDados() {
    try (Connection conn = FabricaConexao.conectar()) {
        String query = "SELECT id, regSocial, cnpj, telefone, email, endereco, represLegal, cpf FROM Fornecedores";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        Vector<String> colunas = new Vector<>();
        colunas.add("ID");
        colunas.add("Registro Social");
        colunas.add("CNPJ");
        colunas.add("Telefone");
        colunas.add("Email");
        colunas.add("Endereço");
        colunas.add("Representante Legal");
        colunas.add("CPF do Representante");

        Vector<Vector<Object>> dados = new Vector<>();
        while (rs.next()) {
            Vector<Object> linha = new Vector<>();
            linha.add(rs.getInt("id"));
            linha.add(rs.getString("regSocial"));
            linha.add(rs.getString("cnpj"));
            linha.add(rs.getString("telefone"));
            linha.add(rs.getString("email"));
            linha.add(rs.getString("endereco"));
            linha.add(rs.getString("represLegal"));
            linha.add(rs.getString("cpf"));
            dados.add(linha);
        }

        tbFornecedores.setModel(new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura
            }
        });
        tbFornecedores.setRowHeight(30);

        // Resetar seleção e campos
        limpaCampos();
        desabilita();

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
         }
    }
}