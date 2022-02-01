package CodeEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class BullsAndCows {
    public static void main(String[] args) throws IOException {
        // BufferedReader 값 입력받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 크기
        int N = 5;
        // 값을 저장할 string
        String str = "";

        for(int i = 0; i<N; i++){
            // rnadom 9
            String rand = Integer.toString((int)(Math.random()*9)+1);
            // add
            str += rand;
        }
//        System.out.println(str);

        // counter
        int bulls = 0; // 모두 일치
        int Cows = 0;  // 숫자 일치
        int totalCount = 0; // 입력 횟수 카운트

        System.out.println("값을 입력해주세요 : ");
        if(br.ready()) {

        }

    }
}
