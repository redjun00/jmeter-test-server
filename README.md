# jmeter-test-server
#### 개요
jmeter로 서버 성능 test case 작성 중 필요한 간단한 서버.

#### 요청 가능 url
POST localhost:8080/fileupload</br>
GET localhost:8080/test</br>
GET localhost:8080/random/gender=a</br>
GET localhost:8080/random?gender=a</br>

####기타 정보
기본 포트 8080</br>
포트 변경 실행 명령어</br>
java -Dserver.port=9000 -jar jmeter-test-server-0.0.1-SNAPSHOT.jar
