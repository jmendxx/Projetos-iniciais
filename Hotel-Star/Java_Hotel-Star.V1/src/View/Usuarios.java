package View;

import Controller.FuncionariosDAO;
import Controller.UsuariosDAO;
import DOA.FabricaConexao;
import Model.ModelFuncionarios;
import Model.ModelUsuarios;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Usuarios extends JFrame {
    private JPanel painelPrincipal;
    private JPanel pinelTopo;
    private JPanel painelBotoes;
    private JPanel painelTabela;
    private JButton bttNovo;
    private JButton bttEditar;
    private JButton bttExcluir;
    private JButton bttSalvar;
    private JButton bttFechar;
    private JTable tbUsuarios;
    private JPasswordField pwfSenha;
    private int idUser; //ID Usuario
    private JTextField txtUser;
    private JComboBox cboxPerfil;
    private JComboBox cBoxFuncionario;
    private String perfil;

    public Usuarios(String perfil) {
        this.perfil = perfil;

        setSize(1200,800);
        JScrollPane scrollPane = new JScrollPane(painelPrincipal, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        setContentPane(scrollPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        carregarFuncionarios();
        carregarDados();

        //******** BOTÃO NOVO ******
        bttNovo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtUser.requestFocus();
                habilita();
                limpaCampos();
            }
        });

        //****** BOTÃO EDITAR *******
        bttEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ModelFuncionarios fSelecionado = (ModelFuncionarios) cBoxFuncionario.getSelectedItem();
                if (fSelecionado != null) {


                    int idFunc = fSelecionado.getId();

                    String funcionario = cBoxFuncionario.getSelectedItem().toString();
                    String user = txtUser.getText().toString();
                    String senha = new String(pwfSenha.getPassword());
                    String perfil = cboxPerfil.getSelectedItem().toString();


                    ModelUsuarios usuario = new ModelUsuarios();
                    usuario.setId(idUser);
                    usuario.setUser(user);
                    usuario.setSenha(senha);

                    UsuariosDAO userDAO = new UsuariosDAO();
                    userDAO .updateUsuarios(usuario );
                    carregarDados();

                }
            }
        });

        //****** BOTÃO DELETAR *******
        bttExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este usuário?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    ModelUsuarios usuario = new ModelUsuarios();
                    usuario.setId(idUser);

                    UsuariosDAO userDAO = new UsuariosDAO();

                    boolean excluido = userDAO.deletaUsuarios(usuario);

                    if (excluido) {
                        JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!");
                        carregarDados();
                        limpaCampos();
                        desabilita();
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao excluir usuário.");
                    }
                }
            }
        });

        //******** BOTÃO SALVAR ******
        bttSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String user = txtUser.getText();
                String senha = new String(pwfSenha.getPassword());
                String perfilSelecionado = (String) cboxPerfil.getSelectedItem();

                ModelFuncionarios fSelecionado = (ModelFuncionarios) cBoxFuncionario.getSelectedItem();

                if (user.isEmpty() || senha.isEmpty() || fSelecionado == null) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos e selecione um funcionário.");
                    return;
                }

                ModelUsuarios usuario = new ModelUsuarios();
                usuario.setId(idUser);
                usuario.setUser(user);
                usuario.setSenha(senha);
                usuario.setPerfil(perfilSelecionado);
                usuario.setIdFunc(fSelecionado.getId());

                UsuariosDAO userDAO = new UsuariosDAO();

                boolean inserido = userDAO.inserirUsuarios(usuario);

                if (inserido) {
                    JOptionPane.showMessageDialog(null, "Informações salvas com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar informações.");
                }

                limpaCampos();
                carregarDados();
                desabilita();
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
        tbUsuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int linhaSelecionada = tbUsuarios.getSelectedRow();
                    if (linhaSelecionada != -1) {
                        habilita();
                        idUser = (int) tbUsuarios.getValueAt(linhaSelecionada, 0);
                        txtUser.setText(tbUsuarios.getValueAt(linhaSelecionada, 1).toString());
                        pwfSenha.setText(tbUsuarios.getValueAt(linhaSelecionada, 2).toString());
                        cboxPerfil.setSelectedItem(tbUsuarios.getValueAt(linhaSelecionada, 3).toString());
                        int idFunc = (int) tbUsuarios.getValueAt(linhaSelecionada, 5);
                        selecionarFuncionarioPorId(idFunc);
                    }

                }
            }
        });

    }

    private void carregarFuncionarios() {
        cBoxFuncionario.removeAllItems();
        List<ModelFuncionarios> listaFuncionarios = FuncionariosDAO.listarTodos();
        for (ModelFuncionarios f : listaFuncionarios) {
            cBoxFuncionario.addItem(f); // Usará toString() da classe Funcionario para mostrar nome + CPF
        }
    }

    private void selecionarFuncionarioPorId(int id) {
        for (int i = 0; i < cBoxFuncionario.getItemCount(); i++) {
            ModelFuncionarios f = (ModelFuncionarios) cBoxFuncionario.getItemAt(i);
            if (f.getId() == id) {
                cBoxFuncionario.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limpaCampos() {
        txtUser.setText("");
        pwfSenha.setText("");
        cboxPerfil.setSelectedIndex(0);
        cBoxFuncionario.setSelectedIndex(-1);
        idUser = 0;
    }

    private void desabilita() {
        txtUser.setEnabled(false);
        pwfSenha.setEnabled(false);
        cboxPerfil.setEnabled(false);
        cBoxFuncionario.setEnabled(false);
        bttEditar.setEnabled(false);
        bttExcluir.setEnabled(false);
        bttSalvar.setEnabled(false);
    }

    private void habilita() {
        txtUser.setEnabled(true);
        pwfSenha.setEnabled(true);
        cboxPerfil.setEnabled(true);
        cBoxFuncionario.setEnabled(true);
        bttEditar.setEnabled(true);
        bttExcluir.setEnabled(true);
        bttSalvar.setEnabled(true);
        bttNovo.setEnabled(true);
    }

    public void carregarDados() {
        try (Connection conn = FabricaConexao.conectar()) {
            String query = "SELECT u.id, u.usuario, u.senha, u.perfil, f.nome AS nomeFuncionario, u.id_funcionario " +
                    "FROM Usuarios u " +
                    "LEFT JOIN Funcionarios f ON u.id_funcionario = f.id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Vector<String> colunas = new Vector<>();
            colunas.add("ID Usuário");
            colunas.add("Usuário");
            colunas.add("Senha");
            colunas.add("Perfil");
            colunas.add("Funcionário");
            colunas.add("ID Func");

            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getInt("id"));
                linha.add(rs.getString("usuario"));
                linha.add(rs.getString("senha"));
                linha.add(rs.getString("perfil"));
                linha.add(rs.getString("nomeFuncionario"));
                linha.add(rs.getInt("id_funcionario"));
                dados.add(linha);
            }

            tbUsuarios.setModel(new DefaultTableModel(dados, colunas));
            tbUsuarios.setRowHeight(30);
            tbUsuarios.getColumnModel().getColumn(4).setMinWidth(0);
            tbUsuarios.getColumnModel().getColumn(4).setMaxWidth(0);
            tbUsuarios.getColumnModel().getColumn(4).setWidth(0);

            tbUsuarios.getColumnModel().getColumn(0).setMinWidth(0);
            tbUsuarios.getColumnModel().getColumn(0).setMaxWidth(0);
            tbUsuarios.getColumnModel().getColumn(0).setWidth(0);

            tbUsuarios.getColumnModel().getColumn(5).setMinWidth(0);
            tbUsuarios.getColumnModel().getColumn(5).setMaxWidth(0);
            tbUsuarios.getColumnModel().getColumn(5).setWidth(0);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
