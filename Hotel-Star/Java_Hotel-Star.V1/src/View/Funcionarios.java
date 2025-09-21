package View;

import Controller.FuncionariosDAO;
import Controller.HospedesDAO;
import DOA.FabricaConexao;
import Model.ModelFornecedores;
import Model.ModelFuncionarios;
import Model.ModelHospedes;

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

public class Funcionarios extends JFrame {
    public JPanel painelPrincipal;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEnd;
    private JTextField txtEmail;
    private JTextField txtTel;
    private JTextField txtDtNasc;
    private JComboBox cbSx;
    private JPanel painelBotoes;
    private JButton bttNovo;
    private JButton bttEditar;
    private JButton bttExcluir;
    private JButton bttSalvar;
    private JButton bttFechar;
    private JTable tbFuncionarios;
    private JTextField txtFuncao;
    private JPanel painelTabela;
    private JPanel painelTopo;
    private String perfil;
    private int idFunc;

    public Funcionarios(String perfil) {
        this.perfil = perfil;

        setSize(1200, 800);
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

                //habilita();
                txtNome.requestFocus();

            }
        });

        //****** BOTÃO EDITAR *******
        bttEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nome = txtNome.getText().toString();
                String cpf = txtCpf.getText().toString();
                String telefone = txtTel.getText().toString();
                String dataN = txtDtNasc.getText().toString();
                String end = txtEnd.getText().toString();
                String email = txtEmail.getText().toString();
                String sexo = String.valueOf(cbSx.getSelectedItem());
                String funcao = txtFuncao.getText().toString();


                ModelFuncionarios funcionario = new ModelFuncionarios();
                funcionario.setNome(nome);
                funcionario.setCpf(cpf);
                funcionario.setEndereco(end);
                funcionario.setTelefone(telefone);
                funcionario.setEmail(email);
                funcionario.setSexo(sexo);
                funcionario.setDataNasc(dataN);
                funcionario.setFuncao(funcao);

                FuncionariosDAO funcpDAO = new FuncionariosDAO();
                funcpDAO.updateFuncionarios(funcionario);
                carregarDados();
            }
        });

        bttNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                habilita();
                limpaCampos();
                txtNome.requestFocus();

            }
        });

        //****** BOTÃO DELETAR *******
        bttExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacao = JOptionPane.showConfirmDialog(
                        null, "Tem certeza que deseja excluir este usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    ModelFuncionarios funcionario = new ModelFuncionarios();
                    funcionario.setId(idFunc);  // Certifique que idFunc tem valor válido!

                    FuncionariosDAO funcDAO = new FuncionariosDAO();
                    boolean excluido = funcDAO.deletaFuncionarios(funcionario);

                    if (excluido) {
                        JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso.");
                        carregarDados();
                        limpaCampos();
                        desabilita();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir usuário.");
                    }

                }
            }
        });

        //******** BOTAO SALVAR *******
        bttSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().toString();
                String cpf = txtCpf.getText().toString();
                String telefone = txtTel.getText().toString();
                String email = txtEmail.getText().toString();
                String end = txtEnd.getText().toString();
                String sexo = String.valueOf(cbSx.getSelectedItem());
                String dataN = txtDtNasc.getText().toString();
                String funcao = txtFuncao.getText().toString();


                ModelFuncionarios funcionario = new ModelFuncionarios();
                funcionario.setNome(nome);
                funcionario.setCpf(cpf);
                funcionario.setTelefone(telefone);
                funcionario.setEmail(email);
                funcionario.setEndereco(end);
                funcionario.setSexo(sexo);
                funcionario.setDataNasc(dataN);
                funcionario.setFuncao(funcao);

                FuncionariosDAO funcDAO = new FuncionariosDAO();

                boolean inserido = funcDAO.inserirFuncionarios(funcionario);

                if (inserido) {
                    JOptionPane.showMessageDialog(null, "Informações salvas com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar informações.");
                }

                limpaCampos();
                desabilita();
                carregarDados();
                bttNovo.setEnabled(true);
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
        tbFuncionarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int linhaSelecionada = tbFuncionarios.getSelectedRow();
                    if (linhaSelecionada != -1) {
                        habilita();
                        idFunc = Integer.parseInt(tbFuncionarios.getValueAt(linhaSelecionada, 0).toString());
                        txtNome.setText(tbFuncionarios.getValueAt(linhaSelecionada, 0).toString());
                        txtCpf.setText(tbFuncionarios.getValueAt(linhaSelecionada, 1).toString());
                        txtTel.setText(tbFuncionarios.getValueAt(linhaSelecionada, 2).toString());
                        txtEmail.setText(tbFuncionarios.getValueAt(linhaSelecionada, 3).toString());
                        txtEnd.setText(tbFuncionarios.getValueAt(linhaSelecionada, 4).toString());
                        cbSx.setSelectedItem(tbFuncionarios.getValueAt(linhaSelecionada, 5).toString());
                        txtDtNasc.setText(tbFuncionarios.getValueAt(linhaSelecionada, 6).toString());
                        txtFuncao.setText(tbFuncionarios.getValueAt(linhaSelecionada, 7).toString());
                    }
                }
            }
        });

    }

    public void limpaCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTel.setText("");
        txtDtNasc.setText("");
        txtEmail.setText("");
        txtEnd.setText("");
        txtFuncao.setText("");
        cbSx.setSelectedIndex(0);
    }
    public void desabilita(){
        txtNome.setEnabled(false);
        txtCpf.setEnabled(false);
        txtTel.setEnabled(false);
        txtDtNasc.setEnabled(false);
        txtEmail.setEnabled(false);
        txtEnd.setEnabled(false);
        cbSx.setSelectedIndex(-1);
        bttEditar.setEnabled(false);
        bttExcluir.setEnabled(false);
        bttSalvar.setEnabled(false);
    }
    public void habilita(){
        txtNome.setEnabled(true);
        txtCpf.setEnabled(true);
        txtTel.setEnabled(true);
        txtDtNasc.setEnabled(true);
        txtEmail.setEnabled(true);
        txtEnd.setEnabled(true);
        cbSx.setSelectedIndex(-1);
        bttEditar.setEnabled(true);
        bttExcluir.setEnabled(true);
        bttSalvar.setEnabled(true);
        bttNovo.setEnabled(true);
    }
    //******** PREENCHE TABELA *******************************************************************
    public void carregarDados() {
        // Conectar ao banco de dados e obter os dados
        try (Connection conn = FabricaConexao.conectar()) {
            String query = "SELECT id,nome, cpf, telefone, email, " +
                    "endereco, sexo, dataNascimento, funcao FROM Funcionarios";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Obter as colunas da tabela
            Vector<String> colunas = new Vector<>();
            colunas.add("Id");
            colunas.add("Nome");
            colunas.add("CPF");
            colunas.add("Telefone");
            colunas.add("Email");
            colunas.add("Endereço");
            colunas.add("Sexo");
            colunas.add("Data de Nascimento");
            colunas.add("Funçao");


            // Obter os dados da tabela
            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("id"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getString("cpf"));
                linha.add(rs.getString("telefone"));
                linha.add(rs.getString("email"));
                linha.add(rs.getString("endereco"));
                linha.add(rs.getString("sexo"));
                linha.add(rs.getString("dataNascimento"));
                linha.add(rs.getString("funcao"));
                dados.add(linha);
            }
            // Criar a tabela com os dados e colunas
            tbFuncionarios.setModel(new DefaultTableModel(dados, colunas));
            tbFuncionarios.setRowHeight(30);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
