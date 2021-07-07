package com.company;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;


public class ListeningThread extends Thread{ // 서버에서 보낸 메세지를 읽는 Thread
    Socket socket = null;

    public ListeningThread(Socket socket) { // 생성자
        this.socket = socket; // 받아온 Socket Parameter 를 해당 클래스 Socket 에 넣기
    }

    public void run() {
        try {
            // InputStream - bring a message from server to client
            // put InputStream information of socket in InputStream in
            InputStream input = socket.getInputStream();
            // use putting the InputStream in BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            while(true) {
                System.out.println(reader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
