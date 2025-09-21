package View;

import DOA.FabricaConexao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Relatorios extends JFrame{
    private JPanel painelPrincipal;
    private JPanel painelTopo;
    private JPanel painelTabela;
    private JPanel painelBotoes;
    private JComboBox comboBox1;
    private JTable tbRelatorios;
    private JButton bttGerar;
    private JButton bttSair;

    private String perfil;

    public Relatorios(String perfil) {
        this.perfil = perfil;
        setSize(1200, 800);
        JScrollPane scrollPane = new JScrollPane(painelPrincipal, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        setContentPane(scrollPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarCombo();

        setVisible(true);

        bttGerar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarRelatorio();
            }
        });

        bttSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Home(perfil);
                dispose();
            }
        });
    }


    private void carregarCombo() {
        comboBox1.addItem("Reservas");
        comboBox1.addItem("Hospedes");
        comboBox1.addItem("Quartos");
        comboBox1.addItem("Produtos");
        comboBox1.addItem("Funcionarios");
        comboBox1.addItem("Fornecedores");
        comboBox1.addItem("Usuarios");
    }

    private void gerarRelatorio() {
        String relatorio = (String) comboBox1.getSelectedItem();

        String query = "";
        Vector<String> colunas = new Vector<>();
        System.out.println("Relatório selecionado: " + relatorio);

        if (relatorio.equalsIgnoreCase("Reservas")) {
            query = "SELECT r.id, h.id AS idHospede, h.nome AS nomeHospede, h.cpf AS cpfHospede, " +
                    "r.dataCheckin, r.dataCheckout, q.id AS idQuarto, q.numero AS numeroQuarto, " +
                    "q.tipo AS tipoQuarto, r.diaria, q.valorDiaria AS valorDiaria, r.valorTotal " +
                    "FROM Reservas r " +
                    "JOIN Hospedes h ON r.idHospede = h.id " +
                    "JOIN Quartos q ON r.idQuarto = q.id";
            colunas.add("ID");
            colunas.add("Nome Hóspede");
            colunas.add("CPF Hóspede");
            colunas.add("Check-In");
            colunas.add("Check-Out");
            colunas.add("Número Quarto");
            colunas.add("Tipo Quarto");
            colunas.add("Diária");
            colunas.add("Valor Diária");
            colunas.add("Valor Total");

        } else if (relatorio.equalsIgnoreCase("Hospedes")){
        query = "SELECT id, nome, cpf, dataNascimento, telefone, email, endereco, sexo FROM Hospedes";
        colunas.add("ID");
        colunas.add("Nome");
        colunas.add("CPF");
        colunas.add("Data de Nascimento");
        colunas.add("Telefone");
        colunas.add("Email");
        colunas.add("Endereço");
        colunas.add("Sexo");

        } else if (relatorio.equalsIgnoreCase("Quartos")) {
            query = "SELECT id, numero, andar, tipo, valorDiaria FROM Quartos";
            colunas.add("Id");
            colunas.add("Número");
            colunas.add("Andar");
            colunas.add("Tipo");
            colunas.add("Valor da Diária");

        } else if (relatorio.equalsIgnoreCase("Produtos")) {
            query = "SELECT id, descricao, tamanho, estoque, fornecedor, cnpj FROM Produtos";
            colunas.add("Id");
            colunas.add("Descrição");
            colunas.add("Tamanho");
            colunas.add("Estoque");
            colunas.add("Fornecedor");
            colunas.add("CNPJ");

        } else if (relatorio.equalsIgnoreCase("Funcionarios")) {
            query = "SELECT id, nome, cpf, telefone, email, endereco, sexo, dataNascimento, funcao FROM Funcionarios";
            colunas.add("Id");
            colunas.add("Nome");
            colunas.add("CPF");
            colunas.add("Telefone");
            colunas.add("Email");
            colunas.add("Endereço");
            colunas.add("Sexo");
            colunas.add("Data de Nascimento");
            colunas.add("Função");

        } else if (relatorio.equalsIgnoreCase("Fornecedores")) {
            query = "SELECT id, regSocial, cnpj, telefone, email, endereco, represLegal, cpf FROM Fornecedores";
            colunas.add("ID");
            colunas.add("Registro Social");
            colunas.add("CNPJ");
            colunas.add("Telefone");
            colunas.add("Email");
            colunas.add("Endereço");
            colunas.add("Representante Legal");
            colunas.add("CPF do Representante");

        } else if (relatorio.equalsIgnoreCase("Usuarios")) {
            query = "SELECT u.id, u.usuario, u.senha, u.perfil, f.nome AS nomeFuncionario " +
                    "FROM Usuarios u LEFT JOIN Funcionarios f ON u.id_funcionario = f.id";
            colunas.add("ID");
            colunas.add("Usuário");
            colunas.add("Senha");
            colunas.add("Perfil");
            colunas.add("Funcionário");

        } else {
            JOptionPane.showMessageDialog(this, "Relatório não definido: " + relatorio, "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = FabricaConexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Vector<Vector<Object>> dados = new Vector<>();

            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                for (int i = 1; i <= colunas.size(); i++) {
                    linha.add(rs.getObject(i));
                }
                dados.add(linha);
            }

            tbRelatorios.setModel(new DefaultTableModel(dados, colunas));
            tbRelatorios.setRowHeight(30);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao gerar relatório:\n" + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
