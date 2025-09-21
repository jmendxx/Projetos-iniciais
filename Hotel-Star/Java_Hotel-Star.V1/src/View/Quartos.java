package View;

import Controller.QuartosDAO;
import DOA.FabricaConexao;
import Model.ModelQuartos;

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

public class Quartos extends JFrame {
    private JPanel painelPrincipal;
    private JPanel painelTopo;
    private JPanel painelBotoes;
    private JPanel painelTabela;
    private JTable tbQuartos;
    private JButton bttNovo;
    private JButton bttEditar;
    private JButton bttExcluir;
    private JButton bttSalvar;
    private JButton bttFechar;
    private JTextField txtNum;
    private JTextField txtAndar;
    private JTextField txtTipo;
    private JTextField txtVlDiaria;
    private String perfil;
    private int idQto;

    public Quartos(String perfil) {
        this.perfil = perfil;

        setSize(1200,800);
        JScrollPane scrollPane = new JScrollPane(painelPrincipal,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
                txtNum.requestFocus();

            }
        });

        //****** BOTÃO EDITAR *******
        bttEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numero = txtNum.getText();
                String andar = txtAndar.getText().toString();
                String tipo = txtTipo.getText().toString();
                String diaria= txtVlDiaria.getText().toString();


                ModelQuartos quarto = new ModelQuartos();
                quarto.setId(idQto);
                quarto.setNum(Integer.parseInt(numero));
                quarto.setAndar(andar);
                quarto.setTipo(tipo);
                quarto.setTipo(diaria);

                QuartosDAO qtoDAO = new QuartosDAO();
                qtoDAO.updateQuartos(quarto);
                carregarDados();
            }
        });

        //****** BOTÃO DELETAR *******
        bttExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacao = JOptionPane.showConfirmDialog(
                        null, "Tem certeza que deseja excluir este usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
                ModelQuartos quarto = new ModelQuartos();
                if (confirmacao == JOptionPane.YES_OPTION) {

                    quarto.setId(idQto);
                }
                QuartosDAO qtoDAO = new QuartosDAO();
                qtoDAO.deletaQuartos(quarto);

                carregarDados();
                limpaCampos();
            }
        });

        //****** BOTAO SALVAR *******
        bttSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String numero = txtNum.getText().toString();
                String andar = txtAndar.getText().toString();
                String tipo = txtTipo.getText().toString();
                String diaria= txtVlDiaria.getText().toString();

                ModelQuartos quarto = new ModelQuartos();
                quarto.setNum(Integer.parseInt(numero));
                quarto.setAndar(andar);
                quarto.setTipo(tipo);
                quarto.setTipo(diaria);

                QuartosDAO qtoDAO = new QuartosDAO();
                qtoDAO.inserirQuartos(quarto);
                limpaCampos();
                desabilita();
                bttNovo.setEnabled(true);
                carregarDados();

                JOptionPane.showMessageDialog(null, "Informações salvo com sucesso!");

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
        tbQuartos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int linhaSelecionada = tbQuartos.getSelectedRow();
                if (linhaSelecionada != -1) {
                    habilita();
                    idQto = (int) tbQuartos.getValueAt(linhaSelecionada, 0); // pega o id da linha selecionada
                    txtNum.setText(tbQuartos.getValueAt(linhaSelecionada, 1).toString());
                    txtAndar.setText(tbQuartos.getValueAt(linhaSelecionada, 2).toString());
                    txtTipo.setText(tbQuartos.getValueAt(linhaSelecionada, 3).toString());
                    txtVlDiaria.setText(tbQuartos.getValueAt(linhaSelecionada, 4).toString());

                }
            }
        });

        carregarDados();
        desabilita();
    }

    public void limpaCampos() {
        txtNum.setText("");
        txtAndar.setText("");
        txtTipo.setText("");
        txtVlDiaria.setText("");
    }

    public void desabilita() {
        txtNum.setEnabled(false);
        txtAndar.setEnabled(false);
        txtTipo.setEnabled(false);
        txtVlDiaria.setEnabled(false);
        bttEditar.setEnabled(false);
        bttExcluir.setEnabled(false);
        bttSalvar.setEnabled(false);
    }
    public void habilita() {
        txtNum.setEnabled(true);
        txtAndar.setEnabled(true);
        txtTipo.setEnabled(true);
        txtVlDiaria.setEnabled(true);
        bttEditar.setEnabled(true);
        bttExcluir.setEnabled(true);
        bttSalvar.setEnabled(true);
        bttNovo.setEnabled(true);
    }
    public void carregarDados() {
        try (Connection conn = FabricaConexao.conectar()) {
            String query = "SELECT id, numero, andar, tipo, valorDiaria FROM Quartos";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Vector<String> colunas = new Vector<>();
            colunas.add("Id");
            colunas.add("Número");
            colunas.add("Andar");
            colunas.add("Tipo");
            colunas.add("Valor da Diaria");


            Vector<Vector<Object>> dados = new Vector<>();
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getInt("id"));
                linha.add(rs.getString("numero"));
                linha.add(rs.getString("andar"));
                linha.add(rs.getString("tipo"));
                linha.add(rs.getString("valorDiaria"));
                dados.add(linha);
            }

            tbQuartos.setModel(new DefaultTableModel(dados, colunas));
            tbQuartos.setRowHeight(30);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
