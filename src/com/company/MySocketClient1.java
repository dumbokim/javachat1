package com.company;

import java.io.IOException;
import java.net.Socket;

public class MySocketClient1 {

    public static void main(String[] args) {
        try {
            Socket socket = null;

//            InetAddress ia = InetAddress.getByName( "tcp://6.tcp.ngrok.io:10923");
//            String ip = ia.getHostAddress();

//            System.out.println(ip);

            //소켓 서버에 접속
            socket = new Socket("127.0.0.1", 8080);

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
