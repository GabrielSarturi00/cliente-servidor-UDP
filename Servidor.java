import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Servidor {

    private static List<Pessoa> pessoas = new ArrayList<>();
    private static Random random = new Random();

    public static void main(String[] args) {
        try {
            Comunicador comunicador = new Comunicador(5000);
            System.out.println("Servidor UDP rodando na porta 5000...");

            while (true) {
                DatagramPacket pacote = comunicador.receber();
                String mensagem = Comunicador.texto(pacote);
                System.out.println("Recebido: " + mensagem);

                String resposta = processar(mensagem);

                comunicador.enviar(resposta, pacote.getAddress(), pacote.getPort());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Protocolo:
    //   CADASTRO;nome;email  -> OK;mensagem  ou  ERRO;mensagem
    //   TOKEN;email          -> TOKEN;chave;segundosRestantes  ou  ERRO;mensagem
    private static String processar(String mensagem) {
        String[] partes = mensagem.split(";", 3);

        if (partes[0].equals("CADASTRO") && partes.length == 3) {
            return cadastrar(partes[1], partes[2]);
        }
        if (partes[0].equals("TOKEN") && partes.length >= 2) {
            return gerarToken(partes[1]);
        }
        return "ERRO;Comando invalido";
    }

    private static String cadastrar(String nome, String email) {
        if (buscar(email) != null) {
            return "ERRO;Usuario ja cadastrado";
        }
        pessoas.add(new Pessoa(nome, email));
        return "OK;Cadastro realizado com sucesso";
    }

    private static String gerarToken(String email) {
        Pessoa pessoa = buscar(email);
        if (pessoa == null) {
            return "ERRO;Usuario nao cadastrado";
        }
        if (!pessoa.tokenValido()) {
            int numero = 100000 + random.nextInt(900000); // 6 digitos
            pessoa.setToken(String.valueOf(numero));
        }
        return "TOKEN;" + pessoa.getToken() + ";" + pessoa.segundosRestantes();
    }

    private static Pessoa buscar(String email) {
        for (Pessoa p : pessoas) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                return p;
            }
        }
        return null;
    }
}
