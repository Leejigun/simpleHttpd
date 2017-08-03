# 웹서버 만들기 실습

### STEP_00 

- 비어있는 maven 프로젝트

### STEP_01

- main메소드를 가지고 있는 App클래스
- main메소드에서는 HttpServer객체를 생성하고, 해당 객체가 가지고 있는 run()메소드를 호출한다.
- run()메소드를 가지고 있는 HttpServer 클래스
- run()메소드에서는 ServerSocket이 Client접속을 기다리도록 한다.

![클래스 다이어그램](1.png)

- 점선은 의존관계를 표현한다. 메소드에서 해당 객체를 사용한다는 것을 의미한다.
- 실선은 연관관계를 표현한다. 클래스가 해당 클래스를 가진다는 것을 의미한다.


###  STEP_02

- RequestHandler클래스는 Client Socket을 파라미터로 받아 Request객체를 반환하는 handle메소드를 가지고 있다.
- Request객체는 필드로 httpVersion, headers, method, requestTarget, bodyInput 등을 가지고 있다.

![클래스 다이어그램](2.png)

- handle메소드는 socket의 inputStream을 이용하여 정보를 읽어들여 httpVersion, headers, method, requestTarget의 값을 초기화 한다.
- 아래는 http://127.0.0.1:8080/snapshot.png 를 브라우저로 요청했을 때 request가 가지는 값에 대한 예제이다.
- http프로토콜의 헤더와 빈줄을 읽어들이고 나서, 해당 InputStream은 Request에 담아서 리턴한다. (Body에서 아직 읽어들일것이 남아있는 InputStream)

![Request에 담겨야 할 값 예제](6.png)

### STEP_03

- ResponseHandler클래스는 requestTarget문자열과 Client Socket을 파라미터로 받아 Response객체를 반환하는 handle메소드를 가지고 있다.
- Request객체는 필드로 httpVersion, headers, method, requestTarget, bodyInput을 가지고 있다.
- http프로토콜의 응답 헤더와 빈줄을 출력하고 나서, 해당 OutputStream을 Response에 담아서 리턴한다. (Body에 출력을 해야할 OutputStream)


![클래스 다이어그램](3.png)

### STEP_04

- DefaultServlet클래스를 작성한다.
- DefaultServlet은 앞에서 구한 Request와 Response를 받아들이는 service메소드를 가지고 있다.

![클래스 다이어그램](4.png)

### STEP_05

- DefaultServlet의 service메소드를 구현한다.
- service메소드에서는 request의 requestTarget에 있는 파일을 webapp폴더아래에서 찾아 읽어들여, response가 가지고 있는 OutputStream 을 이용하여 출력한다.

![클래스 다이어그램](5.png)
 
### STEP_06

- 동시에 여러개의 요청을 처리할 수 있도록 Thread 처리를 하도록 한다.

### STEP_07 (생각해볼 문제)

- 사용자가 요청한 파일이 없을 경우 404 error처리를 할 수 있도록  수정한다.





