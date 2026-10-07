# Sistemas Distribuídos - Cliente-Servidor UDP com Token

Aplicação cliente-servidor em Java usando UDP (classe `Comunicador`) e interface gráfica Swing no cliente.

## Funcionamento
- **Cadastro:** o cliente envia nome completo e e-mail; o servidor guarda em uma lista de `Pessoa` e recusa e-mail duplicado.
- **Token:** o cliente solicita um token aleatório (6 dígitos) com validade de 60 segundos. Se pedir de novo dentro do prazo, o servidor devolve o mesmo token; depois disso, gera um novo.

## Arquivos
- `Comunicador.java` - envio e recebimento de dados via UDP
- `Pessoa.java` - dados do usuário e do token
- `Servidor.java` - servidor UDP (porta 5000)
- `Cliente.java` - cliente com interface Swing

## Como executar
Requer JDK 8 ou superior.

```bash
javac *.java
java Servidor      # em um terminal
java Cliente       # em outro terminal
```

No cliente, use IP `127.0.0.1` e porta `5000` (se o servidor estiver na mesma máquina).
