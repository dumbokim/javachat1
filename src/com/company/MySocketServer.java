package com.company;

// io => 입출력을 위한 패키지
// 이렇게 길게 쓸거면 java.io.* 한 줄만 써도 된다.
import java.io.*;
/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
 */

// 소켓통신을 사용하기 위한 import
import java.net.ServerSocket;
import java.net.Socket;

// 크기가 가변적인 배열인 ArrayList 사용
import java.util.ArrayList;

// 비동기 병렬처리를 위한 멀티 스레드 사용
// 스레드가 하나여도 원하는대로 순서를 변경하는 비동기를 사용할 수 있지만 멀티 스레드는 동시에!
// !! 한 번에 많은 처리를 수행할 경우 병목현상이 일어날 수 있음
public class MySocketServer extends Thread{
    static ArrayList<Socket> list = new ArrayList<Socket>(); //유저를 할당할 ArrayList
    static Socket socket = null; // socket

    // 생성자
    public MySocketServer(Socket socket) {
        this.socket = socket; //유저 socket 을 할당
        list.add(socket); //유저를 list 에 추가
    }

    // Thread 에서 start() 메소드 사용 시 run 수행 (Thread 별로 개별적 수행)
    public void run() {
        try {
            //연결 확인용
            System.out.println("서버: " + socket.getInetAddress() + "IP의 클라이언트와 연결되었습니다.");

            //InputStream - 클라이언트에서 보낸 메세지 읽기
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            //OutputStream - 서버에서 클라이언트로 메세지 보내기
            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);

            //클라이언트에게 연결됐다는 메세지 보내기
            writer.println("서버에 연결되었습니다. ID를 입력해주세요");

            String readValue; // Client 에서 보낸 값 저장
            String name = null; // 클라이언트 이름 설정용
            boolean identify = false;

            //클라이언트가 메세지 입력시마다 수행 (!! 입력을 받을 때마다 name을 어떻게 구별하는지 잘 모르겠음 !!)
            while ((readValue = reader.readLine()) != null) {

                // 연결 후 한 번만 노출
                if (!identify) {
                    name = readValue; // 이름 할당
                    identify = true;
                    writer.println(name + "님이 접속하셨습니다.");
                    continue;
                }

                //list 안에 client 정보가 담겨있음
                // 입력을 받으면 list 내의 모든 client 에게 출력해줌.
                for (int i = 0; i < list.size(); i++) {
                    out = list.get(i).getOutputStream();
                    writer = new PrintWriter(out, true);
                    //클라이언트에게 메세지 발송
                    writer.println(name + " : " + readValue);
                    writer.println(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); //예외처리
        }
    }

    public static void main(String[] args) {
        // 예외처리를 위한 try - catch 구문
        try {
            int socketPort = 5678;
            ServerSocket serverSocket = new ServerSocket(socketPort);

            //서버가 오픈되면 출력
            System.out.println("포트 : " + socketPort + "으로 서버가 열렸습니다.");

            //소켓 서버가 종료될 때까지 무한 루프'
            while(true){
                // 소켓 서버로 접속 시 socketUser에 접속자 정보 할당
                Socket socketUser = serverSocket.accept();
                //Thread 안에 클라이언트 정보를 담아줌
                Thread thd = new MySocketServer(socketUser);
                thd.start(); // Thread 시작
            }
        } catch (IOException e) { // IOException 발생 시 이를 처리
            e.printStackTrace(); // 에러 메세지의 발생 근원지를 찾아서 단계별로 에러를 출력한다.
        }
    }
}
