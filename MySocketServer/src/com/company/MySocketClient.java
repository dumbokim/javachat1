package com.company;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocketClient {

    public static void main(String[] args) {
        try {
            Socket socket = null;
            //소켓 서버에 접속
            socket = new Socket("url or ip"/* tcp url from ngrok or ip */, 8080/* tcp port from ngrok or port */);
            System.out.println("서버에 접속 성공!"); //접속 확인

            // 서버에서 보낸 메세지 읽는 Thread
            ListeningThread t1 = new ListeningThread(socket);
            WritingThread t2 = new WritingThread(socket); //서버로 보내는 Thread

            t1.start();
            t2.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
