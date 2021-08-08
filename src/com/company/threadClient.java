package com.company;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class threadClient extends Thread{

    static Socket socket = null;

    public threadClient(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

            while(true) {
                String readValue = reader.readLine();
                if (readValue.equals("quit")) {
                    System.out.println("접속이 종료됩니다");
                    break;
                } else {
                    System.out.println(readValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Socket socket = null; //소켓생성

            //소켓 서버에 접속
            socket = new Socket("127.0.0.1", 5678); //연결요청보냄
            System.out.println("connect server!"); //접속 성공

            threadClient client = new threadClient(socket);
            client.start();

            Scanner scanner = new Scanner(System.in); //채팅용 scanner
            while(true) {
                try {
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    // 무한반복하면서 입력 수행
                    while(true) {
                        writer.println(scanner.nextLine()); //입력한 메세지 발송
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}