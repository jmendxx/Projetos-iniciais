package Model;

public class ModelFornecedores {
    private int id;
    private String regSocial; //Registro Social
    private String cnpj;
    private String endereco;
    private String telefone;
    private String email;
    private String represLegal; //Representante Legal
    private String cpf;
    private String perfil;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegSocial() {
        return regSocial;
    }

    public void setRegSocial(String regSocial) {
        this.regSocial = regSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRepresLegal() {
        return represLegal;
    }

    public void setRepresLegal(String represLegal) {
        this.represLegal = represLegal;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
