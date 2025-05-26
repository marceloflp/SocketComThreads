import socket
import struct

def escrever_utf(saida, mensagem):
    msg_bytes = mensagem.encode('utf-8')
    saida.sendall(struct.pack('>H', len(msg_bytes)) + msg_bytes)

def ler_utf(mensagem):
    entrada = mensagem.recv(2)
    if not entrada:
        return None
    msg = struct.unpack('>H', entrada)[0]
    dados = b''
    while len(dados) < msg:
        parte = mensagem.recv(msg - len(dados))
        if not parte:
            break
        dados += parte
    return dados.decode('utf-8')

def main():
    HOST = 'localhost'
    PORTA = 5500

    try:
        conexao = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        conexao.connect((HOST, PORTA))
        entrada = conexao
        saida = conexao

        print("Conectado ao servidor. Digite 'sair' para encerrar.")
        while True:
            mensagem = input("> ")
            if mensagem.lower() == 'sair':
                break
            escrever_utf(saida, mensagem)
            resposta = ler_utf(entrada)
            if resposta is None:
                print("Conexão encerrada pelo servidor.")
                break
            print("Resposta:", resposta)

    except Exception as e:
        print("Erro: Conexão Encerrada.")
    finally:
        conexao.close()

if __name__ == '__main__':
    main()

