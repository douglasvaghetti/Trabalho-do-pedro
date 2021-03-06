import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;



public class EnviaDado extends Thread {
	
	 private Socket socket;
     private int quadro;
     private static int CHANCE = 10; //chance de sucesso

	public void run(){
		Random rand = new Random();
		if(rand.nextInt(100)>CHANCE || quadro == -1){
			try {
				try{
					//envia o quadro a nao ser que tenha recebido o sinal de final -1
					PrintWriter out =new PrintWriter(socket.getOutputStream(), true);	
					if(quadro != -1){
						
						out.println(quadro);
						out.flush();
					}else{
						out.println("fim");
						System.out.println("enviou sinal de final");
					}
				}catch(SocketException e){
					System.out.println("Fim de conexão");
				}
			} catch (IOException e) {
				System.out.println("erro ao enviar(de verdade)");
				e.printStackTrace();
			}
		}else{
			System.out.println("erro no envio do quadro "+quadro);
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	// construtor do enviador que recebe o socket e o quadro o enviador
	public EnviaDado(Socket socket, int quadro){
		this.quadro = quadro;
		this.socket= socket;
	}
}
