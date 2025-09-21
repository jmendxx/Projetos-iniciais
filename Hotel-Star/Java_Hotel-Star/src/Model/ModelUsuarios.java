package Model;

public class ModelUsuarios {

    private int id;
    private String user;
    private String senha;
    private String perfil;
    private int idFunc;// ID Funcionario

    public void setIdFunc(int idFunc) {
        this.idFunc = idFunc;
    }

    public int getIdFunc() {
        return idFunc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }


}
