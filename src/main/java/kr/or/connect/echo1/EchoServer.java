package kr.or.connect.echo1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) throws Exception {

        int port = 4444;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Accepted connection from client");

                // socket을 가지고 있는 Thread객체를 생성한다.
                ClientThread clientThread = new ClientThread(clientSocket);
                clientThread.start();
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            serverSocket.close();
        }
    }
}


class ClientThread extends Thread{
    private Socket clientSocket;
    public ClientThread(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String line = null;

            while ((line = br.readLine()) != null) { // 클라이언트로부터 한줄을 읽어들인다.
                System.out.println("echo : " + line);
                pw.println("echo : " + line); // 클라이언트에게 한줄을 출력한다.
                pw.flush();
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                clientSocket.close();
            }catch(Exception ce){
                ce.printStackTrace();
            }
        } // finally
    } // run

}
