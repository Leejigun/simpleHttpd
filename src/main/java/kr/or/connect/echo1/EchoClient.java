package kr.or.connect.echo1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
    public static void main(String args[]) throws Exception{
        Socket server = new Socket("127.0.0.1", 4444);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br = new BufferedReader(new InputStreamReader(server.getInputStream()));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));

        String line = null;
        String readLine = null;
        while((line = keyboard.readLine()) != null){ // 키보드로부터 한줄 읽어들임
            pw.println(line); // server에게 한줄 보냄
            pw.flush();
            readLine = br.readLine(); // server가 보내는 한줄을 읽어들임
            System.out.println(readLine); // server가 보내는 한줄을 출력함
        }
        server.close();
    }
}
