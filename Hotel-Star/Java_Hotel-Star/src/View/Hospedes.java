package View;

import Controller.HospedesDAO;
import DOA.FabricaConexao;
import Model.ModelHospedes;
import Model.ModelUsuarios;

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

public class Hospedes extends JFrame {

    private JPanel painelPrincipal;
    private JTextField txtTelefone;
    private JTextField txtCpf;
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtEnd;
    private JButton bttSalvar;
    private JPanel painelTopo;
    private JComboBox cBoxSx;
    private JPanel painelBotoes;
    private JButton bttNovo;
    private JButton bttEditar;
    private JButton bttExcluir;
    private JButton bttFechar;
    private JPanel painelTabela;
    private JTable tbHospedes;
    private JTextField txtDtNasc;
    private String perfil;
    private int idHosp;

    public Hospedes(String perfil) {
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


                habilita();
                limpaCampos();
                txtNome.requestFocus();

            }
        });

        //****** BOTÃO EDITAR *******
        bttEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().toString();
                String cpf = txtCpf.getText().toString();
                String telefone = txtTelefone.getText().toString();
                String dtNasc = txtDtNasc.getText().toString();
                String end = txtEnd.getText().toString();
                String email = txtEmail.getText().toString();
                String sexo = String.valueOf(cBoxSx.getSelectedItem());


                ModelHospedes hospede = new ModelHospedes();
                hospede.setNome(nome);
                hospede.setCpf(cpf);
                hospede.setEndereco(end);
                hospede.setTelefone(telefone);
                hospede.setEmail(email);
                hospede.setSexo(sexo);
                hospede.setDataNasc(dtNasc);


                HospedesDAO hospDAO = new HospedesDAO();
                hospDAO.updateHospedes(hospede);
                carregarDados();
            }
        });

        //****** BOTÃO DELETAR *******
        bttExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModelHospedes hospede = new ModelHospedes();
                int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este usuário?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {

                    hospede.setId(idHosp);
                }

                HospedesDAO hospDAO = new HospedesDAO();
                hospDAO.deletaHospedes(hospede);
                carregarDados();
                limpaCampos();
            }
        });

        //****** BOTAO SALVAR *******
        bttSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String nome = txtNome.getText().toString();
                String cpf = txtCpf.getText().toString();
                String telefone = txtTelefone.getText().toString();
                String dataN = txtDtNasc.getText().toString();
                String end = txtEnd.getText().toString();
                String email = txtEmail.getText().toString();
                String sexo = String.valueOf(cBoxSx.getSelectedItem());

                ModelHospedes hospede = new ModelHospedes();
                hospede.setNome(nome);
                hospede.setCpf(cpf);
                hospede.setDataNasc(dataN);
                hospede.setTelefone(telefone);
                hospede.setEmail(email);
                hospede.setEndereco(end);
                hospede.setSexo(sexo);


                HospedesDAO hospDAO = new HospedesDAO();

                boolean inserido = hospDAO.inserirHospedes(hospede);

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
        tbHospedes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int linhaSelecionada = tbHospedes.getSelectedRow();
                    habilita();
                    if (linhaSelecionada != -1) {
                        // Preencher os campos de texto com os dados da linha selecionada

                        txtNome.setText(tbHospedes.getValueAt(linhaSelecionada, 0).toString());
                        txtCpf.setText(tbHospedes.getValueAt(linhaSelecionada, 1).toString());
                        txtEnd.setText(tbHospedes.getValueAt(linhaSelecionada, 2).toString());
                        txtTelefone.setText(tbHospedes.getValueAt(linhaSelecionada, 3).toString());
                        txtEmail.setText(tbHospedes.getValueAt(linhaSelecionada, 4).toString());
                        cBoxSx.setSelectedItem(tbHospedes.getValueAt(linhaSelecionada, 5).toString());
                        txtDtNasc.setText(tbHospedes.getValueAt(linhaSelecionada, 6).toString());
                    }
                }
            }
        });

    }

    private void limpaCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtDtNasc.setText("");
        txtEnd.setText("");
        txtEmail.setText("");
        cBoxSx.setSelectedIndex(0);
    }

    private void desabilita() {
        txtNome.setEnabled(false);
        txtCpf.setEnabled(false);
        txtTelefone.setEnabled(false);
        txtDtNasc.setEnabled(false);
        txtEnd.setEnabled(false);
        txtEmail.setEnabled(false);
        cBoxSx.setEnabled(false);
        bttEditar.setEnabled(false);
        bttExcluir.setEnabled(false);
        bttSalvar.setEnabled(false);
    }

    private void habilita() {
        txtNome.setEnabled(true);
        txtCpf.setEnabled(true);
        txtTelefone.setEnabled(true);
        txtDtNasc.setEnabled(true);
        txtEnd.setEnabled(true);
        txtEmail.setEnabled(true);
        cBoxSx.setEnabled(true);
        bttEditar.setEnabled(true);
        bttExcluir.setEnabled(true);
        bttSalvar.setEnabled(true);
        bttNovo.setEnabled(true);
    }

    public void carregarDados() {
        try (Connection conn = FabricaConexao.conectar()) {
            String query = "SELECT id, nome, cpf, dataNascimento, telefone, email, endereco, sexo FROM Hospedes";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Vector<String> colunas = new Vector<>();
            colunas.add("ID");
            colunas.add("Nome");
            colunas.add("CPF");
            colunas.add("Data de Nascimento");
            colunas.add("Telefone");
            colunas.add("Email");
            colunas.add("Endereço");
            colunas.add("Sexo");

            Vector<Vector<Object>> dados = new Vector<>();

            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getInt("id"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getString("cpf"));
                linha.add(rs.getString("dataNascimento"));
                linha.add(rs.getString("telefone"));
                linha.add(rs.getString("email"));
                linha.add(rs.getString("endereco"));
                linha.add(rs.getString("sexo"));
                dados.add(linha);
            }

            tbHospedes.setModel(new DefaultTableModel(dados, colunas));
            tbHospedes.setRowHeight(30);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
