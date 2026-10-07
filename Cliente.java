import java.awt.GridLayout;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cliente extends JFrame {

    private JTextField txtIp = new JTextField("127.0.0.1");
    private JTextField txtPorta = new JTextField("5000");
    private JTextField txtNome = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JButton btnCadastrar = new JButton("Cadastrar");
    private JButton btnToken = new JButton("Solicitar Token");
    private JLabel lblToken = new JLabel("Token: -");
    private JTextArea areaLog = new JTextArea(8, 30);

    private Comunicador comunicador;

    public Cliente() {
        super("Cliente UDP - Token");

        JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
        painel.add(new JLabel("IP do servidor:"));
        painel.add(txtIp);
        painel.add(new JLabel("Porta:"));
        painel.add(txtPorta);
        painel.add(new JLabel("Nome completo:"));
        painel.add(txtNome);
        painel.add(new JLabel("E-mail:"));
        painel.add(txtEmail);
        painel.add(btnCadastrar);
        painel.add(btnToken);

        areaLog.setEditable(false);

        add(painel, "North");
        add(lblToken, "Center");
        add(new JScrollPane(areaLog), "South");

        try {
            comunicador = new Comunicador();
            comunicador.setTimeout(3000); // espera no maximo 3s pela resposta
        } catch (Exception e) {
            areaLog.append("Erro ao criar comunicador: " + e.getMessage() + "\n");
        }

        btnCadastrar.addActionListener(e -> {
            String msg = "CADASTRO;" + txtNome.getText() + ";" + txtEmail.getText();
            String resposta = enviarEReceber(msg);
            areaLog.append("Cadastro: " + resposta + "\n");
        });

        btnToken.addActionListener(e -> {
            String msg = "TOKEN;" + txtEmail.getText();
            String resposta = enviarEReceber(msg);
            String[] partes = resposta.split(";");
            if (partes[0].equals("TOKEN")) {
                lblToken.setText("Token: " + partes[1] + " (valido por " + partes[2] + "s)");
            } else {
                areaLog.append("Token: " + resposta + "\n");
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String enviarEReceber(String mensagem) {
        try {
            InetAddress ip = InetAddress.getByName(txtIp.getText());
            int porta = Integer.parseInt(txtPorta.getText());
            comunicador.enviar(mensagem, ip, porta);
            return Comunicador.texto(comunicador.receber());
        } catch (Exception ex) {
            return "ERRO;" + ex.getMessage();
        }
    }

    public static void main(String[] args) {
        new Cliente();
    }
}
