package Model;

public class ModelReservas {
    private int id;
    private int idHospede;
    private String nome;
    private String cpf;
    private String checkIn;
    private String checkOut;
    private int idQuarto;
    private String tipoQuarto;
    private String andar;
    private double valorDiaria;
    private int diaria;
    private double valorTotal;
    private String formaPagamento;

    public int getIdHospede() {
        return idHospede;
    }

    public void setIdHospede(int idHospede) {
        this.idHospede = idHospede;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCheckIn() {
        return checkIn;
    }
    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }
    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getIdQuarto() {
        return idQuarto;
    }
    public void setIdQuarto(int numeroQuarto) {
        this.idQuarto = numeroQuarto;
    }

    public String getTipoQuarto() {
        return tipoQuarto;
    }
    public void setTipoQuarto(String tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }
    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public int getDiaria() {
        return diaria;
    }
    public void setDiaria(int diaria) {
        this.diaria = diaria;
    }

    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getAndar() {
        return andar;
    }
    public void setAndar(String andar) {
        this.andar = andar;
    }
}
