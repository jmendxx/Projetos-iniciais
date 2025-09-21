package View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame{
    private JPanel jpHome;
    private JPanel jpTopo;
    private JPanel jpRodape;
    private JMenuBar jmBar;
    private JMenu jmCadastro;
    private JMenu jmReservas;
    private JMenu jmRelatorios;
    private JMenu jmSair;
    private JLabel jlImgfundo;
    private JMenuItem jmiHospedes;
    protected JMenuItem jmiCTQuartos;
    protected JMenuItem jmiFornecedores;
    protected JMenuItem jmiFuncionarios;
    protected JMenuItem jmiProdutos;
    private JMenuItem jmiRelatorios;
    private JMenuItem jmiRsQuartos;
    private JMenu jmconfig;
    //private JMenuItem jmItemquarto;
    private JMenuItem jmiSair;
    protected JMenuItem jmiCdUser;
    private String perfil;

    public Home(String perfil) {
        this.perfil = perfil;

        //Criação da Tela Home
        setSize(1200, 800);
        JScrollPane scrollPane = new JScrollPane(jpHome, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        setContentPane(scrollPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        if (perfil.equals("Funcionario")) {
            jmiCTQuartos.setEnabled(false);
            jmiFornecedores.setEnabled(false);
            jmiFuncionarios.setEnabled(false);
            jmiCdUser.setEnabled(false);
            jmiProdutos.setEnabled(false);
            jmiRelatorios.setEnabled(false);
            jmiCdUser.setEnabled(false);

        } else if (perfil.equals("Gerente")) {
            jmiCdUser.setEnabled(false);
        }

        setVisible(true);

        // Cadastro de hóspedes
        jmiHospedes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Hospedes(perfil);
                dispose();
            }
        });

        //Cadastro de quartos
        jmiCTQuartos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Quartos(perfil);
                dispose();
            }
        });

        //Cadastro de Funcionarios
        jmiProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Produtos(perfil);
                dispose();
            }
        });

        //Cadastro de fornecedor
        jmiFornecedores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Fornecedores(perfil);
                dispose();
            }
        });

        //Cadastro de Funcionarios
        jmiFuncionarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Funcionarios(perfil);
                dispose();
            }
        });

        //Reserva dos quartos
        jmiRsQuartos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Reservas(perfil);
                dispose();
            }
        });

        jmiRelatorios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Relatorios(perfil);
                dispose();
            }
        });

        // Cadastro Usuario
        jmiCdUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Usuarios(perfil);
                dispose();
            }
        });

        //Botão de sair
        jmiSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });
    }
}
