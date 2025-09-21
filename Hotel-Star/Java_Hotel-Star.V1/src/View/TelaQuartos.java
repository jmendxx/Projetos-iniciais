package View;

import Controller.QuartosDAO;
import DOA.FabricaConexao;
import Model.ModelQuartos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class TelaQuartos extends JFrame {
    private JPanel painelTopo;
    private JPanel painelPrincipal;
    private JTable tabelaTQuartos;
    private JPanel painelTabela;
    private JButton bttSelecionar;
    private JButton bttCancelar;
    private JPanel painelBotoes;
    private Reservas reservas;

    private ModelQuartos quartoSelecionado;

    public TelaQuartos(Reservas reservas) {
        this.reservas = reservas;

        setTitle("Selecionar Quarto");
        setSize(700, 400);
        JScrollPane scrollPane = new JScrollPane(painelPrincipal,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        setContentPane(scrollPane);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarDados();


        tabelaTQuartos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabelaTQuartos.getSelectedRow();
                if (linha != -1) {
                    quartoSelecionado = new ModelQuartos();
                    quartoSelecionado.setId((int) tabelaTQuartos.getValueAt(linha, 0));
                    quartoSelecionado.setNum((int) tabelaTQuartos.getValueAt(linha, 1));
                    quartoSelecionado.setAndar(tabelaTQuartos.getValueAt(linha, 2).toString());
                    quartoSelecionado.setTipo(tabelaTQuartos.getValueAt(linha, 3).toString());
                    quartoSelecionado.setValorDiaria((double) tabelaTQuartos.getValueAt(linha, 4));
                    quartoSelecionado.setStatus(tabelaTQuartos.getValueAt(linha, 5).toString());

                    if (quartoSelecionado.getStatus().equalsIgnoreCase("Ocupado")) {
                        JOptionPane.showMessageDialog(null,
                                "Este quarto está ocupado!",
                                "Aviso", JOptionPane.WARNING_MESSAGE);
                        quartoSelecionado = null;
                    } else if (quartoSelecionado.getStatus().equalsIgnoreCase("Manutenção")) {
                        JOptionPane.showMessageDialog(null,
                                "Este quarto está em manutenção!",
                                "Aviso", JOptionPane.WARNING_MESSAGE);
                        quartoSelecionado = null;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Quarto selecionado: " + quartoSelecionado.getNum(),
                                "Quarto Selecionado", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        bttSelecionar.addActionListener(e -> {
            if (quartoSelecionado != null) {
                reservas.setQuartoSelecionado(quartoSelecionado);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Selecione um quarto disponível antes de confirmar.",
                        "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });

        bttCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void carregarDados() {
        try {
            List<ModelQuartos> quartos = QuartosDAO.listarTodos();

            Vector<String> colunas = new Vector<>();
            colunas.add("ID");
            colunas.add("Número");
            colunas.add("Andar");
            colunas.add("Tipo");
            colunas.add("Valor Diária");
            colunas.add("Status");

            Vector<Vector<Object>> dados = new Vector<>();

            for (ModelQuartos q : quartos) {
                Vector<Object> linha = new Vector<>();
                linha.add(q.getId());
                linha.add(q.getNum());
                linha.add(q.getAndar());
                linha.add(q.getTipo());
                linha.add(q.getValorDiaria());
                linha.add(q.getStatus());
                dados.add(linha);
            }

            tabelaTQuartos.setModel(new DefaultTableModel(dados, colunas));
            tabelaTQuartos.setRowHeight(25);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar dados dos quartos",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ModelQuartos getQuartoSelecionado() {
        return quartoSelecionado;
    }

}
