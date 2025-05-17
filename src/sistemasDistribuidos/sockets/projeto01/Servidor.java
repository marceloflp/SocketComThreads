package sistemasDistribuidos.sockets.projeto01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Servidor {

	public static void main(String[] args) {

		Set<String> listaCliente = Collections.synchronizedSet(new HashSet<>());

		final int PORTA = 5500;
		final int MAX_CONEXAO = 5;

		System.out.println("SERVIDOR AGUARDANDO CONEXÃO...");
		
		try (ServerSocket conexao = new ServerSocket(PORTA);
				Socket cliente = conexao.accept();
				DataInputStream entrada = new DataInputStream(cliente.getInputStream());
				DataOutputStream saida = new DataOutputStream(cliente.getOutputStream())) {
			
			
			if (!listaCliente.contains(cliente.getLocalAddress().getHostAddress())) {
				if (listaCliente.size() < MAX_CONEXAO) {
					listaCliente.add(cliente.getLocalAddress().getHostAddress());
					System.out.println("CONEXÃO ESTABELECIDA! \nHOST: " + cliente.getLocalAddress().getHostAddress());
					while (true) {
						String output = entrada.readUTF();
						if (output.equalsIgnoreCase("comando:1")) {
							LocalDate data = LocalDate.now();
							SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
							String dataformatada = formatoData.format(data);

							saida.writeUTF(dataformatada);
							continue;
						}

						if (output.equalsIgnoreCase("comando:2")) {
							SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
							Date dataHoraAtual = new Date();
							String horaAtual = sdf.format(dataHoraAtual);
							saida.writeUTF(horaAtual);
							continue;						}

						if (output.equalsIgnoreCase("comando:3")) {
							String infoCliente = "IP: " + cliente.getLocalAddress().getHostAddress() + "\nPORTA: "
									+ PORTA;
							saida.writeUTF(infoCliente);
							continue;
						}

						if (output.equalsIgnoreCase("comando:4")) {
							for (String ips : listaCliente) {
								System.out.println("IP: " + ips);
								;
							}
							saida.writeUTF("");
							continue;
						}
						
						if(output.equalsIgnoreCase("sair")) {
							System.out.println("Conexão com " + cliente.getLocalAddress().getHostAddress() + "Encerrada");
							break;
						}
						
						saida.writeUTF("");
						System.out.println(output);

					}
				}
			} else {
				System.out.println("ERRO: MÁQUINA JÁ ESTÁ EM CONEXÃO!");
			}
		} catch (IOException ex) {

		}
	}

}