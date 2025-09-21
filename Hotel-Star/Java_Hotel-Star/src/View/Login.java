package View;

import Controller.LoginDAO;
import Model.ModelUsuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame{
    public JPanel jpLogin;
    private JPanel jpTopo;
    private JTextField jtxtUsuario;
    private JPasswordField jpwtxtSenha;
    private JComboBox comboBox1;
    private JButton btnEntrar;
    private JLabel jlUsuario;
    private JLabel jlIcone;
    private JLabel jlSenha;
    private JButton btnSair;
    private JPanel jpLateral;


    public Login() {

        setTitle("Login");
        setSize(500, 500);
        setContentPane(jpLogin);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        jtxtUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jtxtUsuario.setBackground(new Color(238,232,170));
            }


            @Override
            public void focusLost(FocusEvent e) {
                jtxtUsuario.setBackground(new Color(249,234,195));

            }
        });

        jpwtxtSenha.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                jpwtxtSenha.setBackground(new Color(238,232,170));
            }

            @Override
            public void focusLost(FocusEvent e) {
                jpwtxtSenha.setBackground(new Color(249,234,195));
            }

        });

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = jtxtUsuario.getText();
                String senha = new String(jpwtxtSenha.getPassword());
                String perfil = String.valueOf(comboBox1.getSelectedItem());

                LoginDAO loginDAO = new LoginDAO();
                ModelUsuarios resultado = loginDAO.validarLogin(usuario, senha, perfil);

                if (resultado != null) {
                    String perfilUsuario = resultado.getPerfil();
                    System.out.println("Login bem-sucedido!");
                    new Home(perfilUsuario);
                    dispose();
                } else {
                    System.out.println("Usuário ou senha inválidos.");
                    jtxtUsuario.setText("");
                    jpwtxtSenha.setText("");
                    jtxtUsuario.requestFocus();
                    JOptionPane.showMessageDialog(Login.this, "Usuário ou senha incorretos!");
                }
            }
        });
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
