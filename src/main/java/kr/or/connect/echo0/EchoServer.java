package kr.or.connect.echo0;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class EchoServer {

    public static void main(String[] args) throws Exception {

        int port = 4444;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        Socket clientSocket = serverSocket.accept();
        System.err.println("Accepted connection from client");

        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        String line = null;

        while((line = br.readLine()) != null){ // 클라이언트로부터 한줄을 읽어들인다.
            System.out.println("echo : " + line);
            pw.println("echo : " + line); // 클라이언트에게 한줄을 출력한다.
            pw.flush();
        }
        clientSocket.close();
        serverSocket.close();
    }
}
