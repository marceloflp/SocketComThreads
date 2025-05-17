package sistemasDistribuidos.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

	public static void main(String[] args) {
		final int PORTA = 12345;
		
		System.out.println("Servidor Aguardando conexão na porta " + PORTA);
		
		try(ServerSocket servidor = new ServerSocket(PORTA);
				Socket conexao = servidor.accept();
				DataInputStream entrada =  new DataInputStream(conexao.getInputStream());
				DataOutputStream saida =  new DataOutputStream(conexao.getOutputStream());
				Scanner input = new Scanner(System.in)) {
			

			String mensagem;
			String mensagemSaida;
			while(true) {
				System.out.println("Cliente "+ conexao.getInetAddress().getHostAddress() +" conectado!");
				
				while((mensagem = entrada.readUTF()) != null) {
					System.out.println("Cliente: " + mensagem);
					
					System.out.print("> ");
					mensagemSaida = input.nextLine();
					
					saida.writeUTF(mensagemSaida);
				}
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
