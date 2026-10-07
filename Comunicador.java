import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Comunicador {

    private DatagramSocket socket;

    // Cliente: porta escolhida automaticamente
    public Comunicador() throws SocketException {
        socket = new DatagramSocket();
    }

    // Servidor: porta fixa
    public Comunicador(int porta) throws SocketException {
        socket = new DatagramSocket(porta);
    }

    public void setTimeout(int milissegundos) throws SocketException {
        socket.setSoTimeout(milissegundos);
    }

    public void enviar(String mensagem, InetAddress ip, int porta) throws IOException {
        byte[] dados = mensagem.getBytes(StandardCharsets.UTF_8);
        DatagramPacket pacote = new DatagramPacket(dados, dados.length, ip, porta);
        socket.send(pacote);
    }

    public DatagramPacket receber() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket pacote = new DatagramPacket(buffer, buffer.length);
        socket.receive(pacote);
        return pacote;
    }

    public static String texto(DatagramPacket pacote) {
        return new String(pacote.getData(), 0, pacote.getLength(), StandardCharsets.UTF_8);
    }

    public void fechar() {
        socket.close();
    }
}
