# 멀티 쓰레드 Echo Server만들기

- ServerSocket.accept()메소드는 블럭킹 메소드로 사용자 클라이언트가 접속을 하게 되면 해당 클라이언트와 통신하기 위한 Socket객체가 반환된다.
- 여러개의 Client가 동시에 접속을 하려면, accept()메소드가 클라이언트가 접속할때마다 동작해야한다. 어떻게 그것이 가능할까?
- ServerSocket의 accept()메소드는 무한루프에서 실행된다.
- 클라이언트가 접속하면 해당 socket을 쓰레드에 넘겨 실행되도록 한다. 클라이언트 접속이 끊어지면 해당 쓰레드는 종료된다.
- 100개의 클라이언트가 접속한다면, Main쓰레드 하나와 100개의 ClientThread가 동작하게 된다.

```
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

```