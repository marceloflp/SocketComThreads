package sistemasDistribuidos.sockets.projeto01;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Comunicacao implements Runnable{

	private Socket conexao;
	private DataInputStream entrada;
	private DataOutputStream saida;
	
	public Comunicacao(Socket conexao, DataInputStream entrada, DataOutputStream saida) throws IOException {
		super();
		this.conexao = conexao;
		this.entrada = new DataInputStream(conexao.getInputStream());
		this.saida = new DataOutputStream(conexao.getOutputStream());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
