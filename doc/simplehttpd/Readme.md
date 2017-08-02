# bootcamp-personal-project

개인 과제 프로젝트
============

목표
----
- 그 동안 학습해 온 내용을 정리하고 해당 내용을 응용해서 시간내에 간단한 과제를 수행해 봅니다.
- 개인별 기술 이해 및 습득 수준을 점검해 봅니다

기본RULE
--------
- 혼자 진행
- 인터넷 사용가능
- 멘토 도움을 받지 않음
- 조원을 비롯한 다른 멤버들의 코드를 참고하지 않음
- 필요시 기존 자신의 프로젝트나 속한 조의 프로젝트를 참고하는 건 무방하나 프로젝트를 통째로 복사하거나 파일 복사로 가져오는 건 금지
    - 필요하다면 눈으로 보고 직접 타이핑
- 적절한 중간 커밋과 커밋 메시지를 작성
    - 과제 다 완료 후 코드 커밋 한 개로 프로젝트를 끝내지 않도록 할 것
        - 미리 계획을 세우는 걸 추천

진행절차
-------
- github에서 프로젝트 생성 - 프로젝트 이름 : httpd
- 해당 프로젝트에 멤버로 멘토를 추가


과제
===

개요
----
- 간단한 HTTP Server를 만들어봅니다.

기본사항
-------
- maven 프로젝트로 만들어서 컴파일 및 빌드를 진행합니다.
- Java 7 이상을 사용합니다.
- 기본 서버 포트는 8080을 사용합니다.
- HTTP server 가 전체 구현되어 있는 JDK 내외의 HTTP Server 라이브러리는 사용하지 않습니다.
- 응답 헤더를 구현하기 시작한 이후부터는 상태 코드를 올바르게 구현 처리합니다.
- 각 Step의 Hint는 최소한의 힌트입니다. 추가로 필요한 부분은 직접 구현 및 찾아내야 합니다.
    - 굳이 힌트를 따르지 않아도 무방합니다. 더 나은 방법이 있다면 해당 방식으로 구현하세요!
- 테스트 코드를 같이 만들 수 있으면 + 가점 입니다.
- 클래스 필드에 대한 getter/setter 는 따로 언급하지 않습니다. 필요시 적절히 만드세요.
- 또한 지정된 메소드 이외의 메소드를 필요한 만큼 알아서 만드는 걸 권장합니다.
- 애매하다고 느껴지거나 고민되는 부분은 본인이 알아서 판단하거나 정합니다.

Step 0. ServerSocket 과 Socket 과 Echo Server
---
- 이클립스에서 project를 만듭니다.
    - maven 프로젝트로 만듭니다.
    - groupId 와 artifactId는 배운대로 적절히 정합니다.

Step 1. HttpServer 객체 생성
---

- HttpServer는 Thread객체입니다.
- HttpServer 는 생성자로 portNumber를 넘겨받아서 내부에 저장하고
- run() 메소드를 호출하면 서버가 시작되도록 적절히 수정합니다.
```
    HttpServer httpServer = new HttpServer(8080);
    httpServer.run();
```

코드를 실행한 다음 브라우저로 접속합니다.

- 이클립스 콘솔에 Request 헤더가 정상적으로 출력되었는지 확인합니다.
- http://localhost:8080 으로 접근했을때 브라우저에 `Hello world`를 표시되도록 합니다.
- 브라우저에 Hello world! 메시지를 출력하는 부분은 PrintWriter 를 이용합니다.
    - https://docs.oracle.com/javase/8/docs/api/java/io/PrintWriter.html


Step 2. Text File Serving
---

- 프로젝트 root 바로 아래에 webapp 폴더를 하나 만듭니다.
- index.html 을 만들고 간단한 Html을 만들어서 본문(body)에 Hello world! 를 p 태그로 입력합니다.
    
```
...
...
</head>
<body>
  <p>Hello index.html world!</p>
</body>
...
```
- http://localhost:8080 접근했을때 index.html을 읽어 들여서 보여주도록 변경합니다.
- Hint
    - https://docs.oracle.com/javase/tutorial/essential/io/streams.html
    - 구글 검색어: java inputstream bufferedreader
    - 상대경로로 접근하고자 할 때는 webapp/index.html 로 지정

Step 3. Request
---
- Request 클래스를 만들어서 Step 1에서 그냥 출력해 버리고 지나쳐 버린 Request Header 정보를 저장하도록 코드를 수정합니다.
- Class Fields
    - String method: GET/POST 등의 메소드, request line을 파싱해서 추출가능
    - String requestTarget: https://tools.ietf.org/html/rfc7230#section-3.1.1
    - String httpVersion: "HTTP/1.1"로 상수 고정
    - Map headers: String key/value 쌍으로 만들어지는 헤더정보. 구분자(delimiter)인 ":"를 포함시킬지 여부는 본인이 결정
- Method
    - request 라인을 적절히 parsing 해서 method 필드와 requestTarget 필드에 값을 넣어주는 메소드
    - header line을 입력받으면 key/value 로 headers에 넣는 메소드
    - header key 값을 이용해서 header value를 돌려주는 메소드
- 확인
    - 브라우저를 통해 http://localhost:8080 으로 접근했을때 `Hello world`가 여전히 표시됩니다.
- Hint
    - HEADER 마지막을 구분해 내는 건 `while (!"".equals(line)) {}` 로 처리 가능합니다.
    - 문자열 처리: https://docs.oracle.com/javase/tutorial/java/data/manipstrings.html

Step 4. Response (part-1)
--
- PrintWriter를 이용해서 Response Status Line 및 Header를 추가합니다. (아래 코드는 필요한 전체 코드가 아니니 유의할것!)
```
...
    out.println("HTTP/1.0 200 OK");
    out.println("Content-Type: text/html");
    out.println("Server: 127.0.0.1");
...
```
- 확인
    - 브라우저를 통해 http://localhost:8080 으로 접근했을때 여전히 index.html이 잘 보이는지 확인합니다.
    - 만약 헤더를 추가했더니 페이지가 보이지 않게 되면 어떤 부분을 놓쳤는지 HTTP response spec을 떠올려서 파악해봅니다.

Step 4. Response (part-2)
---
- Response 클래스를 만들어서 Response Status 및 Header 정보를 저장합니다.
- Step 2 에서 만들어진 코드에 ResponseHeader 클래스를 이용해서 헤더 정보 보내는 걸로 변경합니다.
- Class Fields
    - String httpVersion: "HTTP/1.1"로 상수 고정
    - String statusCode: 응답코드
    - String reasonPhrase: reason phrase
    - Map headers: key/value 쌍으로 만들어지는 헤더정보. 구분자(delimiter)인 ":"를 포함시킬지 여부는 본인이 결정
- Method
    - Response status line 을 돌려주는 메소드
- 응답헤더에서 다음 헤더는 꼭 처리하세요
    - Server
    - Content-Type
    - Content-Length
- 확인
    - 브라우저를 통해 http://localhost:8080 으로 접근했을때 여전히 index.html이 잘 보이는지 확인합니다.
- Hint
    - 구글 검색어: java files readallbytes

Step 5. Logging
---
- 모든 서버 요청(Request)에 대해서는 로그를 남깁니다.
- exception 시에도 에러 메시지를 로그로 남깁니다.
- 필수 로그 내용은 시간과 요청페이지, client 정보(본인이 결정) 입니다.
- 본인 조에서 사용한 logging library를 이용합니다.
- logback.xml 설정 파일의 내용도 적절히 지정합니다.
- Hint
    - maven dependency 처리하기


Step 6. Handler
---
- Request 처리와 Response 처리를 담당하는 RequestHandler와 ResponseHandler 클래스를 만듭니다.
- HttpServer 클래스의 산재되어 있는 Request 처리 로직과 Response 처리 로직을 RequestHandler와 ResponseHandler 이동합니다.
- 각각의 생성자는 아래와 같습니다.
```
public class RequestHandler {
    public Request handle(Socket clientSocket){
    ...
```

```
public class ResponseHandler {
    public Response handle(Socket clientSocket)
    ...
```
- handle 메소드에서는 Request 객체나 Response 객체를 내부에서 생성해서 요청처리와 응답을 수행하게 만듭니다.
- 이 작업을 진행하면 HttpServer 클래스의 run method는 기존에 비해 훨씬 깔끔하게 정리가 됩니다.
- 확인
    - 브라우저를 통해 http://localhost:8080 으로 접근했을때 여전히 index.html이 잘 보이는지 확인합니다.
- Hint
    - socket 등이 의도치 않게 끊기지 않도록 유의하세요

Step 7. Request Handle
---
- 첨부파일 세 개를 내려 받아 webapp 아래에 위치시킵니다. (example.html, basic.css, snapshot.png)
- 브라우저에서 요청한 경로의 파일을 적절하게 브라우저로 전송하도록 코드를 수정합니다.
    - http://127.0.0.1:8080/example.html 입력하면 페이지가 보여야 합니다.
- 요청한 경로의 파일은 앞서 만든 Request 클래스를 이용해서 위치를 알아내고 읽어들입니다.
- 존재 하지 않는 resource 경로를 요청한 경우 적절한 응답코드와 에러 메시지를 보여줍니다.
- 확인
    - 브라우저를 통해 http://localhost:8080/example.html 으로 접근했을때 페이지가 보입니다.
    - 브라우저를 통해 http://localhost:8080/snapshot.png로 접근했을때 이미지가 보입니다.
- Hint
    - 응답헤더의 Content-Length, Content-Type 에 유의하세요
    - Content-Type을 적절히 잘 지정하면 브라우저에서 example.html 을 요청했을때 css와 image까지 포함된 형태로 잘 보이게 만들수 있습니다.
    - snapshot.png 파일은 text 파일이 아닙니다. readline 으로 읽어들여도 문제가 없을지, 있다면 어떻게 읽어서 보낼지 생각해보세요.


--첨부파일--
- [signup.html](webapp/signup.html)
- [fail.html](webapp/fail.html)
- [loginForm.html](webapp/loginForm.html)
- [welcome.html](webapp/welcome.html)
- [Example.html](webapp/Example.html)
- ![snapshot.png](webapp/snapshot.png)
- [basic.css](webapp/basic.css)