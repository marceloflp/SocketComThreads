package sistemasDistribuidos.sockets.projeto01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comunicacao implements Runnable {

	private Socket conexao;
	private DataInputStream entrada;
	private DataOutputStream saida;

	public Comunicacao(Socket conexao) throws IOException {
		super();
		this.conexao = conexao;
		this.entrada = new DataInputStream(conexao.getInputStream());
		this.saida = new DataOutputStream(conexao.getOutputStream());
	}

	@Override
	public void run() {
		String ip = conexao.getInetAddress().getHostAddress();
		try {

			while (true) {
				String comando = entrada.readUTF();
				System.out.println("Recebido de " + ip + ": " + comando);

				switch (comando.toLowerCase()) {
				case "comando:1":
					String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					saida.writeUTF("Data atual: " + data);
					break;

				case "comando:2":
					String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
					saida.writeUTF("Hora atual: " + hora);
					break;

				case "comando:3":
					String info = "Servidor IP: " + conexao.getLocalAddress().getHostAddress() + " | Porta: "
							+ conexao.getLocalPort() + "\nCliente IP: " + conexao.getInetAddress().getHostAddress()
							+ " | Porta: " + conexao.getPort() + "\nStatus: Conexão ativa.";
					saida.writeUTF(info);
					break;

				case "comando:4":
					StringBuilder lista = new StringBuilder("IPs conectados:\n");
					synchronized (Servidor.listaClientes) {
						for (String ipConectado : Servidor.listaClientes) {
							lista.append(ipConectado).append("\n");
						}
					}
					saida.writeUTF(lista.toString());
					break;

				case "sair":
					saida.writeUTF("Conexão encerrada.");
					System.out.println("Cliente " + ip + " desconectou.");
					return;

				default:
					saida.writeUTF("Comando inválido.");
				}
			}
		} catch (IOException e) {
			System.out.println("Erro com cliente " + ip + ": " + e.getMessage());
		} finally {
			try {
				conexao.close();
				entrada.close();
				saida.close();
			} catch (IOException e) {
			}

			synchronized (Servidor.listaClientes) {
				Servidor.listaClientes.remove(ip);
				Servidor.conexoesAtivas.remove(conexao);
				System.out.println("Conexão removida: " + ip);
			}
		}

	}
}
