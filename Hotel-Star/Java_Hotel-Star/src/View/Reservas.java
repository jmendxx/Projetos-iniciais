package View;

import Controller.QuartosDAO;
import Controller.ReservasDAO;
import Model.ModelHospedes;
import Model.ModelQuartos;
import Model.ModelReservas;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Reservas extends JFrame {
    private JPanel painelPrincipal;
    private JPanel painelTopo;
    private JComboBox cBoxNome;
    private JComboBox cBoxCpf;
    private JTextField txtDiaria;
    private JButton bttSQuarto;
    private JTextField txtNQuarto;
    private JTextField txtTipo;
    private JTextField txtVDiaria;
    private JTextField txtAndar;
    private JTextField txtVTotal;
    private JPanel painelQuarto;
    private JButton bttNova;
    private JButton bttEditar;
    private JButton bttReservar;
    private JButton bttCancelar;
    private JButton bttFechar;
    private JPanel painelBotoes;
    private JPanel painelTabela;
    private JTable tabelaReserva;
    private JTextField txtCheckOut;
    private JTextField txtCheckIn;
    private JButton bttSalvar;
    private JComboBox<String> cBoxPagamento;
    private String perfil;

    private int idReservaSelecionada = -1;
    private List<ModelHospedes> listaHospedes = new ArrayList<>();
    private List<ModelQuartos> listaQuartos = new ArrayList<>();
    private int idQuarto;

    public Reservas(String perfil) {
        this.perfil = perfil;
        setSize(1200, 800);
        JScrollPane scrollPane = new JScrollPane(painelPrincipal, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        setContentPane(scrollPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        carregarHospedes();
        carregarQuartos();
        carregarDados();
        preencherCamposQuartoPorNumero();

        txtNQuarto.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                preencherCamposQuartoPorNumero();
            }
        });

        bttNova.addActionListener(e -> {
            limparCampos();
            habilitaCampos(true);
            idReservaSelecionada = -1;
        });

        bttEditar.addActionListener(e -> {
            if (idReservaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma reserva para editar.");
                return;
            }
            habilitaCampos(true);
        });

        bttSalvar.addActionListener(e -> {
            try {
                int idHospede = encontrarIdHospedePorCpf(cBoxCpf.getSelectedItem().toString());
                if (idHospede == -1) {
                    JOptionPane.showMessageDialog(this, "Hóspede não encontrado!");
                    return;
                }

                ModelReservas reserva = construirReserva(idHospede);
                if (reserva == null) {
                    return;
                }

                salvarReserva(reserva);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar reserva: " + ex.getMessage());
            }
        });

        bttFechar.addActionListener(e -> {
            new Home(perfil);
            dispose();
        });

        bttSQuarto.addActionListener(e -> {
            new TelaQuartos(this);
        });

        tabelaReserva.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tabelaReserva.getSelectedRow() != -1) {
                    int linha = tabelaReserva.getSelectedRow();
                    idReservaSelecionada = Integer.parseInt(tabelaReserva.getValueAt(linha, 0).toString());
                    preencherCamposSelecionados(linha);
                    habilitaCampos(false);
                }
            }
        });

        habilitaCampos(false);
    }

    private void limparCampos() {
        cBoxNome.setSelectedIndex(0);
        cBoxCpf.setSelectedIndex(0);
        txtCheckIn.setText("");
        txtCheckOut.setText("");
        txtNQuarto.setText("");
        txtTipo.setText("");
        txtVDiaria.setText("");
        txtDiaria.setText("");
        txtVTotal.setText("");
        txtAndar.setText("");
        cBoxPagamento.setSelectedIndex(0);

    }

    private void habilitaCampos(boolean habilitar) {
        cBoxNome.setEnabled(habilitar);
        cBoxCpf.setEnabled(habilitar);
        txtCheckIn.setEnabled(habilitar);
        txtCheckOut.setEnabled(habilitar);
        txtNQuarto.setEnabled(habilitar);
        txtTipo.setEnabled(habilitar);
        txtVDiaria.setEnabled(habilitar);
        txtDiaria.setEnabled(habilitar);
        txtVTotal.setEnabled(habilitar);
        txtAndar.setEnabled(habilitar);
        cBoxPagamento.setEnabled(habilitar);

        bttSalvar.setEnabled(habilitar);
        bttEditar.setEnabled(!habilitar);
        bttReservar.setEnabled(habilitar);
        bttCancelar.setEnabled(habilitar);
    }



    private void carregarHospedes() {
        try {
            listaHospedes = Controller.HospedesDAO.listarTodos();
            cBoxNome.removeAllItems();
            cBoxCpf.removeAllItems();
            for (ModelHospedes h : listaHospedes) {
                cBoxNome.addItem(h.getNome());
                cBoxCpf.addItem(h.getCpf());
            }

            cBoxNome.addActionListener(e -> {
                int i = cBoxNome.getSelectedIndex();
                if (i >= 0 && i < listaHospedes.size()) cBoxCpf.setSelectedIndex(i);
            });
            cBoxCpf.addActionListener(e -> {
                int i = cBoxCpf.getSelectedIndex();
                if (i >= 0 && i < listaHospedes.size()) cBoxNome.setSelectedIndex(i);
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar hóspedes: " + e.getMessage());
        }
    }

    private void carregarQuartos() {
        try {
            listaQuartos = new QuartosDAO().listarTodos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar quartos: " + e.getMessage());
        }
    }

    private void carregarDados() {
        try {
            ReservasDAO dao = new ReservasDAO();
            List<ModelReservas> lista = dao.listarTodos();

            Vector<String> colunas = new Vector<>();
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

            Vector<Vector<Object>> dados = new Vector<>();

            for (ModelReservas r : lista) {
                Vector<Object> linha = new Vector<>();
                linha.add(r.getId());


                ModelHospedes hospede = buscarHospedePorId(r.getIdHospede());
                if (hospede != null) {
                    linha.add(hospede.getNome());
                    linha.add(hospede.getCpf());
                } else {
                    linha.add("N/A");
                    linha.add("N/A");
                }

                linha.add(r.getCheckIn());
                linha.add(r.getCheckOut());


                ModelQuartos quarto = buscarQuartoPorId(r.getIdQuarto());
                if (quarto != null) {
                    linha.add(quarto.getNum());
                    linha.add(quarto.getTipo());
                } else {
                    linha.add("N/A");
                    linha.add("N/A");
                }

                linha.add(r.getDiaria());
                linha.add(r.getValorDiaria());
                linha.add(r.getValorTotal());

                dados.add(linha);
            }

            tabelaReserva.setModel(new DefaultTableModel(dados, colunas));
            tabelaReserva.setRowHeight(30);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar reservas: " + e.getMessage());
        }
    }


    private ModelHospedes buscarHospedePorId(int idHospede) {
        for (ModelHospedes h : listaHospedes) {
            if (h.getId() == idHospede) return h;
        }
        return null;
    }


    private ModelQuartos buscarQuartoPorId(int idQuarto) {
        for (ModelQuartos q : listaQuartos) {
            if (q.getId() == idQuarto) return q;
        }
        return null;
    }

    private void preencherCamposSelecionados(int linha) {
        try {

            idReservaSelecionada = Integer.parseInt(tabelaReserva.getValueAt(linha, 0).toString());


            String nome = tabelaReserva.getValueAt(linha, 1).toString();
            String cpf = tabelaReserva.getValueAt(linha, 2).toString();
            cBoxNome.setSelectedItem(nome);
            cBoxCpf.setSelectedItem(cpf);

            txtCheckIn.setText(tabelaReserva.getValueAt(linha, 3).toString());
            txtCheckOut.setText(tabelaReserva.getValueAt(linha, 4).toString());
            txtNQuarto.setText(tabelaReserva.getValueAt(linha, 5).toString());
            txtTipo.setText(tabelaReserva.getValueAt(linha, 6).toString());
            txtDiaria.setText(tabelaReserva.getValueAt(linha, 7).toString());
            txtVDiaria.setText(tabelaReserva.getValueAt(linha, 8).toString());
            txtVTotal.setText(tabelaReserva.getValueAt(linha, 9).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao preencher campos da reserva: " + e.getMessage());
        }
    }


    private void preencherCamposQuartoPorNumero() {
        try {
            String numeroStr = txtNQuarto.getText().trim();
            if (numeroStr.isEmpty()) {
                limparCamposQuarto();
                return;
            }
            int numero = Integer.parseInt(numeroStr);

            for (ModelQuartos q : listaQuartos) {
                if (q.getNum() == numero) {
                    txtTipo.setText(q.getTipo());
                    txtVDiaria.setText(String.valueOf(q.getValorDiaria()));
                    calcularValorTotal();
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Quarto não encontrado.");
            limparCamposQuarto();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número do quarto inválido.");
            limparCamposQuarto();
        }
    }

    private void limparCamposQuarto() {
        txtTipo.setText("");
        txtVDiaria.setText("");
        txtVTotal.setText("");
    }

    private void calcularValorTotal() {
        try {
            double valorDiaria = Double.parseDouble(txtVDiaria.getText().trim());
            int diaria = Integer.parseInt(txtDiaria.getText().trim());
            double total = valorDiaria * diaria;
            txtVTotal.setText(String.format("%.2f", total));
        } catch (Exception e) {
            txtVTotal.setText("");
        }
    }

    private ModelReservas construirReserva(int idHospede) {
        try {
            ModelReservas r = new ModelReservas();

            r.setId(idReservaSelecionada);
            r.setIdHospede(idHospede);
            r.setCheckIn(txtCheckIn.getText().trim());
            r.setCheckOut(txtCheckOut.getText().trim());
            r.setDiaria(Integer.parseInt(txtDiaria.getText().trim()));
            r.setValorDiaria(Double.parseDouble(txtVDiaria.getText().trim()));
            r.setValorTotal(Double.parseDouble(txtVTotal.getText().trim()));
            r.setIdQuarto(idQuarto);

            return r;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao montar reserva: " + e.getMessage());
            return null;
        }
    }

    private void salvarReserva(ModelReservas reserva) {
        try {
            ReservasDAO dao = new ReservasDAO();

            if (idReservaSelecionada == -1) {
                dao.inserirReserva(reserva);
                JOptionPane.showMessageDialog(this, "Reserva criada com sucesso!");
            } else {
                dao.atualizarReserva(reserva);
                JOptionPane.showMessageDialog(this, "Reserva atualizada com sucesso!");
            }

            carregarDados();
            limparCampos();
            habilitaCampos(false);
            idReservaSelecionada = -1;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar reserva: " + ex.getMessage());
        }
    }

    private int encontrarIdHospedePorCpf(String cpf) {
        for (ModelHospedes h : listaHospedes) {
            if (h.getCpf().equals(cpf)) {
                return h.getId();
            }
        }
        return -1;
    }

    private int encontrarIdQuartoPorNumero(int numero) {
        for (ModelQuartos q : listaQuartos) {
            if (q.getNum() == numero) {
                return q.getId();
            }
        }
        return -1;
    }

    private void botaoSalvarAction() {
        try {
            int idHospede = encontrarIdHospedePorCpf(cBoxCpf.getSelectedItem().toString());
            int numeroQuarto = Integer.parseInt(txtNQuarto.getText().trim());
            int idQuarto = encontrarIdQuartoPorNumero(numeroQuarto);

            if (idHospede == -1) {
                JOptionPane.showMessageDialog(this, "Hóspede não encontrado!");
                return;
            }
            if (idQuarto == -1) {
                JOptionPane.showMessageDialog(this, "Quarto não encontrado!");
                return;
            }

            ModelReservas reserva = construirReserva(idHospede);
            if (reserva != null) {
                salvarReserva(reserva);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar reserva: " + e.getMessage());
        }
    }

    public void setQuartoSelecionado(ModelQuartos quarto) {
        if (quarto != null) {
            txtNQuarto.setText(String.valueOf(quarto.getNum()));
            txtTipo.setText(quarto.getTipo());
            txtAndar.setText(quarto.getTipo());
            txtVDiaria.setText(String.valueOf(quarto.getValorDiaria()));
            calcularValorTotal();
        }
    }
}