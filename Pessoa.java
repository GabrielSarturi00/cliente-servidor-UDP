public class Pessoa {

    private String nome;
    private String email;
    private String token;
    private long horaToken; // momento (ms) em que o token foi gerado

    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        this.horaToken = System.currentTimeMillis();
    }

    public boolean tokenValido() {
        return token != null && segundosRestantes() > 0;
    }

    public long segundosRestantes() {
        long passados = (System.currentTimeMillis() - horaToken) / 1000;
        return 60 - passados;
    }
}
