package com.leny;

import java.util.Scanner;

public class App {
    public App() {}
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        System.out.println(line);
        sc.close();
    }
}