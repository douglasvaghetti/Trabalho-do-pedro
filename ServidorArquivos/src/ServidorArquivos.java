

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorArquivos {

    public static int PORTA=50000;
    public static Vector<String> nomesArquivos;
    
    public static void main(String[] args) {
        try {
            ServerSocket servidorSockets = new ServerSocket(PORTA);
            nomesArquivos=new Vector<String>();
            while(true){
                Socket socket = servidorSockets.accept();
                new HandleClient(socket).start();
                System.out.println("novo cliente conectado");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
