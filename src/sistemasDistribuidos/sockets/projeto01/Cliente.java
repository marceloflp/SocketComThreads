package sistemasDistribuidos.sockets.projeto01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {

		final int PORTA = 5500;

		try (Socket conexao = new Socket("localhost", PORTA);
				DataInputStream entrada = new DataInputStream(conexao.getInputStream());
				DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
				Scanner input = new Scanner(System.in)) {

			System.out.println("Conectado ao servidor. Digite 'sair' para terminar.");
			
			String mensagem;
			while(true) {
				System.out.print("> ");
				mensagem = input.nextLine();
				
				if(mensagem.equalsIgnoreCase("sair")) break;
				
				saida.writeUTF(mensagem);
				String resposta = entrada.readUTF();
				System.out.println("Resposta: " + resposta);
			}

		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
