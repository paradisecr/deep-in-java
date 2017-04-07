package me.rui;

import java.util.Scanner;

/**
 * Created by cr on 2017/3/25.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int x[] = new int[n];
        int p[] = new int[n];
        for (int i =0; i < n; i++) {
            x[i] = scanner.nextInt();
            p[i] = scanner.nextInt();
        }
        int e = 0;
        for (int i = 0; i < n; i++) {
            e += x[i] * p[i];
        }
        int head = e/100;
        int tail = e%100;
        System.out.print(head + "." + tail + "0");
    }
}
