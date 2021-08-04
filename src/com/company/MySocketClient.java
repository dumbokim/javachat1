package com.company;

import java.io.IOException;
import java.net.Socket;

public class MySocketClient {

    public static void main(String[] args) {
        try {
            //소켓 서버에 접속
            Socket socket = new Socket("127.0.0.1", 8090);
            if (socket != null) {
                System.out.println("서버에 접속 성공!"); //접속 확인 문구 출력
            } else {
                return ;
            }

            ListeningThread t1 = new ListeningThread(socket); // 서버에서 보낸 메세지 읽는 Listening Thread
            WritingThread t2 = new WritingThread(socket); //서버로 보내는 Writing Thread

            // Thread.start() 를 통해 각 thread 의 run 실행
//            t1.start();
            t2.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
