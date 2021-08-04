package com.company;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class WritingThread extends Thread{ // 서버로 메세지 보내는 Thread
    Socket socket = null;
    Scanner scanner = new Scanner(System.in); //채팅용 scanner

    public WritingThread(Socket socket) { //생성자
        //받아온 socket Parameter 를 해당 클래스 socket 에 넣기
        this.socket = socket;
    }

    // start() 발생 시 수행


    public void run() {
        try {
            //OutputStream - 클라이언트에서 Server 로 메세지 발송
            //socket 의 OutputStream 정보를 OutputStream out 에 넣은 뒤
            //PrintWriter 에 위 OutputStream 을 담아 사용
            PrintWriter writer = new PrintWriter(
                    // socket.getOutputStream()를 StandardCharsets.UTF_8 로 인코해서 넣는다.
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                    true);

            // InputStream : 서버에서 클라이언트로 메세지를 가져옴
            // 이렇게 .getInputStream() 을 이용
            InputStream input = socket.getInputStream();
            // use putting the InputStream in BufferedReader
            // input 을 StandardCharsets.UTF_8 으로 인코딩해서 넣는다
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));



            // 무한 루프를 돌면서 입력이 있으면 수행
            while(true) {
                writer.println(scanner.nextLine()); //입력한 메세지 발송
                System.out.println(reader.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
