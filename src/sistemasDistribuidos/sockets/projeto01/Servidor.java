package sistemasDistribuidos.sockets.projeto01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Servidor {
	public static Set<String> listaClientes = Collections.synchronizedSet(new HashSet<>());
	public static List<Socket> conexoesAtivas = Collections.synchronizedList(new ArrayList<>());
	
	public static void main(String[] args) {

		

		final int PORTA = 5500;
		final int MAX_CONEXOES = 5;

		System.out.println("SERVIDOR AGUARDANDO CONEXÃO...");
		
		try (ServerSocket servidor = new ServerSocket(PORTA)) {
            while (true) {
                Socket cliente = servidor.accept();
                String ipCliente = cliente.getInetAddress().getHostAddress();

                synchronized (listaClientes) {
                    if (listaClientes.contains(ipCliente)) {
                        System.out.println("ERRO: IP já conectado: " + ipCliente);
                        cliente.close();
                        continue;
                    }

                    if (listaClientes.size() >= MAX_CONEXOES) {
                        System.out.println("ERRO: Limite máximo de conexões atingido.");
                        cliente.close();
                        continue;
                    }

                    listaClientes.add(ipCliente);
                    conexoesAtivas.add(cliente);
                    System.out.println("NOVA CONEXÃO DE: " + ipCliente);
                    new Thread(new Comunicacao(cliente)).start();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
	}
	
	

}