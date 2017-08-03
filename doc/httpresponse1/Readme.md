# Http 요청

- 요청에 대한 응답


![response](007.png)

- http://127.0.0.1:8080 으로 요청 했을 때 프로젝트의 README.md 파일의 내용을 읽어들여 브라우저에 출력된다.
- README.md파일은 thml문서가 아니기 때문에 줄바꿈 표시등이 표시되지 않는다.
- http프로토콜은 요청정보를 읽어들인 후, 응답정보를 출력한다.


```
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

        // 브라우저와 연결된 socket으로 부터 브라우저의 요청정보를 읽어들이기 위한 BufferedReader와
        // 브라우저에게 출력하기 위한 OutputStream을 구했다.
        OutputStream out = clientSocket.getOutputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String line = null;


        // request를 body전까지만 읽어들인 후 break한다.
        while((line = br.readLine()) != null){ // 클라이언트로부터 한줄을 읽어들인다.
            System.out.println("readLine : " + line);
            if("".equals(line))
                break;
        }

        // 프로젝트 폴더에 있는 README.md파일의 크기를 구하기 위해서 File객체를 생성했다.
        File f = new File("README.md");
        
        // StringBuffer 객체에 response의 첫줄, 헤더, 빈줄에 대한 정보를 저장한다.
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

        // StringBuffer의 값을 문자열로 반환한 후, 그것을 byte[]로 읽어와서 브라우저에 출력한다.
        out.write(sb.toString().getBytes());
        
        // 실제로 파일정보를 최대 1024바이트 단위로 읽어들여 브라우저에 출력한다.
        // 파일의 크기가 2000 바이트라면 첫번째는 1024 두번째는 나머지를 읽어올 것이다.
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int readCount = 0;
            while((readCount = fis.read(buffer)) != -1){ // 파일의 끝(-1) 까지 읽어들인다.
                out.write(buffer,0,readCount); // buffer의 0번째부터 읽어온 개수만큼 출력한다.
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        clientSocket.close();
        serverSocket.close();
    }
}


```

