package com.company;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class ListeningThread extends Thread{ // 서버에서 보낸 메세지를 읽는 Thread
    Socket socket = null;

    public ListeningThread(Socket socket) { // 생성자
        this.socket = socket; // 받아온 Socket Parameter 를 해당 클래스 Socket 에 넣기
    }

    // start() 발생 시 수행
    public void run() {
        try {
            // InputStream : 서버에서 클라이언트로 메세지를 가져옴
            // 이렇게 .getInputStream() 을 이용
            InputStream input = socket.getInputStream();
            // use putting the InputStream in BufferedReader
            // input 을 StandardCharsets.UTF_8 으로 인코딩해서 넣는다
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

            // 무한 루프를 돌면서 입력이 있으면 수행
            while(true) {
                System.out.println(reader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 콘솔에 퇴장 클라이언트 IP 주소 출력
            System.out.println(socket.getInetAddress() + "의 연결이 종료됨!");
        }
    }
}
