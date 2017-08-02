package kr.or.connect.httpresponse0;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) throws Exception {

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        Socket clientSocket = serverSocket.accept();
        System.err.println("Accepted connection from client");

        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        String line = null;


        // request를 body전까지만 읽어들인 후 break한다.
        while((line = br.readLine()) != null){ // 클라이언트로부터 한줄을 읽어들인다.
            System.out.println("readLine : " + line);
            if("".equals(line))
                break;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("<html>");
        sb.append("<head><title>hello</title></head>");
        sb.append("<body>");
        sb.append("<h1> Hello World </h1>");
        sb.append("</body>");
        sb.append("</html>");

        pw.println("HTTP/1.1 200 OK");
        pw.println("Date: Sat, 09 Oct 2010 14:28:02 GMT");
        pw.println("Server: Apache");
        pw.println("Last-Modified: Tue, 01 Dec 2009 20:18:22 GMT");
        pw.println("Accept-Ranges: bytes");
        pw.println("Content-Length: " + sb.toString().length());
        pw.println("Content-Type: text/html");
        pw.println();
        pw.println(sb.toString());
        pw.flush();

        clientSocket.close();
        serverSocket.close();
    }
}
