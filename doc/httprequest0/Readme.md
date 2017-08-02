# Http 요청

- 참고 문서 : https://developer.mozilla.org/ko/docs/Web/HTTP/Overview

## GET방식 요청

![request](001.png)

- 첫번째 줄은 method path 프로토콜 버전이 나온다.
- 두번째 줄부터 빈줄이 나올때까지 Header정보가 나온다.
- 빈줄이 하나 나온다.
- post방식일 경우에는 빈 줄 이후에 body정보가 전달된다.  


```
package kr.or.connect.httprequest0;

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

        String line = null;

        while((line = br.readLine()) != null){ // 클라이언트로부터 한줄을 읽어들인다.
            System.out.println("readLine : " + line);
            if("".equals(line))
                break;
        }
        clientSocket.close();
        serverSocket.close();
    }
}

```


- 위의 서버를 실행하고 브라우저에서 http://127.0.0.1:8080 을 실행한다.

```
Started server on port 8080
Accepted connection from client
readLine : GET / HTTP/1.1
readLine : Host: 127.0.0.1:8080
readLine : Connection: keep-alive
readLine : Cache-Control: max-age=0
readLine : Upgrade-Insecure-Requests: 1
readLine : User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.78 Safari/537.36
readLine : Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
readLine : Accept-Encoding: gzip, deflate, br
readLine : Accept-Language: ko,en-US;q=0.8,en;q=0.6
readLine : 
```

결과가 위와 같이 출력되는 것을 알 수 있다. / 에 대한 페이지를 요청하는 것을 알 수 있다.