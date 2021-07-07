package com.company;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WritingThread extends Thread{ // 서버로 메세지 보내는 Thread
    Socket socket = null;
    Scanner scanner = new Scanner(System.in); //채팅용 scanner

    public WritingThread(Socket socket) { //생성자
        //받아온 socket Parameter 를 해당 클래스 socket 에 넣기
        this.socket = socket;
    }

    public void run() {
        try {
            //OutputStream - 클라이언트에서 Server 로 메세지 발송
            //socket 의 OutputStream 정보를 OutputStream out 에 넣은 뒤
            OutputStream out = socket.getOutputStream();
            //PrintWriter 에 위 OutputStream 을 담아 사용
            PrintWriter writer = new PrintWriter(out, true);

            while(true) {
                writer.println(scanner.nextLine()); //입력한 메세지 발
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
