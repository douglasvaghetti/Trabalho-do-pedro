import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;


public class Principal {
    public static long[][][] pesos;
    public static int portaPing = 9090;
    public static int portaDados = 9091;
    public static int portaMensagens = 9092;
    public static int Ncomputadores = 5;
    public static Map<String,Integer> mapaIPs;
    public static Integer NComputadoresConhecidos=0;
    public static final Object LockMatrizEDados= new Object();

    public static void main(String[] args) {
        pesos = new long[Ncomputadores][Ncomputadores][2];
        //primeira coluna = peso
        //segunda = hora em que este peso foi lido
        
        try {
            System.out.println("Começou o programa!");
            RespondedorPing respondedorPing= new RespondedorPing();
            respondedorPing.start();
            Pingador pingador = new Pingador(args);  //os ips dos vizinhos sao recebidos como argumentos
            //TODO: mostrar o ip na caixinha de dialogo
            if(JOptionPane.showConfirmDialog(null, "iniciar operação?"+say()) == JOptionPane.CANCEL_OPTION){
                return;
            }
            pingador.start();
            PropagadorDados propagadorDados = new PropagadorDados(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void initMapa(String[] IPsVizinhos){
        synchronized(LockMatrizEDados){
            mapaIPs.put(say(), 0);
            NComputadoresConhecidos++;
            for(int x=1;x<IPsVizinhos.length;x++){
                mapaIPs.put(IPsVizinhos[x], NComputadoresConhecidos);
                NComputadoresConhecidos++;
            }
        }
    }
        
    //TODO: arrumar o say!
     public static  String say(){    
        Enumeration e;
        String retorno="";
        try {
            e = NetworkInterface.getNetworkInterfaces();
            
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements())
                {
                    InetAddress i = (InetAddress) ee.nextElement();
                    System.out.println(i.getHostAddress());
                    retorno+=i.getHostAddress()+'\n';
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
      }    

}
