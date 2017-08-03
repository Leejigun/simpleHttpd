package kr.or.connect.httpresponse1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) throws Exception {

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        Socket clientSocket = serverSocket.accept();
        System.err.println("Accepted connection from client");


        OutputStream out = clientSocket.getOutputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String line = null;


        // request를 body전까지만 읽어들인 후 break한다.
        while((line = br.readLine()) != null){ // 클라이언트로부터 한줄을 읽어들인다.
            System.out.println("readLine : " + line);
            if("".equals(line))
                break;
        }

        File f = new File("README.md");
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP/1.1 200 OK");
        sb.append("");
        sb.append("\n");
        sb.append("Content-Type:text/html; charset=utf-8");
        sb.append("\n");
        sb.append("Status:200 OK");
        sb.append("\n");
        sb.append("Content-Length: " + f.length());
        sb.append("\n");
        sb.append("\n"); // 빈줄

        out.write(sb.toString().getBytes());
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int readCount = 0;
            while((readCount = fis.read(buffer)) != -1){ // 파일의 끝(-1) 까지 읽어들인다.
                out.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        clientSocket.close();
        serverSocket.close();
    }
}
