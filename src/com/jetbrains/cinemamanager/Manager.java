package com.jetbrains.cinemamanager;

import java.util.Arrays;
import java.util.Scanner;

public class Manager {
    static private final Scanner SCANNER = new Scanner(System.in);
    static private char[][] seatingArrangement;
    static private int countSeats;
    static private int cash = 0;

    public static void main(String[] args) {
        setCinemaRoomSize();
        Arrays.stream(seatingArrangement).forEach(rowArr -> Arrays.fill(rowArr, 'S'));
        Menu();
    }

    static void Menu() {
        while (true) {
            System.out.printf("\n1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit%n");
            switch (SCANNER.nextInt()) {
                case 1:
                    printSeatingArrangement();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    getStatistics();
                    break;
                case 0:
                    return;
            }
        }
    }

    static void setCinemaRoomSize() {
        System.out.println("\nEnter the number of rows:");
        seatingArrangement = new char[SCANNER.nextInt()][];
        System.out.println("Enter the number of seats in each row:");
        seatingArrangement = new char[seatingArrangement.length][SCANNER.nextInt()];
        countSeats = seatingArrangement.length * seatingArrangement[0].length;
    }

    static void printSeatingArrangement() {
        System.out.println("\nCinema:");
        System.out.print(" ");
        for (int i = 1; i <= seatingArrangement[0].length; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 0; i < seatingArrangement.length; i++) {
            System.out.print(i+1 + " ");
            for (int j = 0; j < seatingArrangement[i].length; j++) {
                System.out.print(seatingArrangement[i][j] +" ");
            }
            System.out.println("");
        }
    }

    static void buyTicket() {
        System.out.println("\nEnter a row number:");
        int rowNum = SCANNER.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seatNum = SCANNER.nextInt();
        setBuyTicket(rowNum, seatNum);

    }
    static void setBuyTicket(int row, int seat) {
        if (row > seatingArrangement.length | seat > seatingArrangement[0].length){
            System.out.println("\nWrong input!");
            buyTicket();
            return;
        }
        if (seatingArrangement[row - 1][seat - 1] == 'B') {
            System.out.println("\nThat ticket has already been purchased!");
            buyTicket();
            return;
        }
        System.out.printf("\nTicket price: $%d\n", getTicketPrice(row));
        seatingArrangement[row - 1][seat - 1] = 'B';
        cash += getTicketPrice(row);
    }

    static int getTicketPrice(int rowNumber) {
        if (countSeats > 60) {
            return rowNumber > seatingArrangement.length / 2 ? 8 : 10;
        } else {
            return 10;
        }
    }

    static void getStatistics() {
        int countBuyTicket = 0;
        for (char arr[] : seatingArrangement){
            for (char x : arr){
                if (x == 'B') countBuyTicket++;
            }
        }
        double countBuyTicketInPer = (double) countBuyTicket * 100/countSeats ;
        int tolalCash;
        if (countSeats <= 60) tolalCash = countSeats * 10;
        else {
            int firstrow = (int) Math.floor(seatingArrangement.length/2);
            int secondrow = seatingArrangement.length - firstrow;
            tolalCash = firstrow * 10 * seatingArrangement[0].length + secondrow * 8 * seatingArrangement[0].length;
        }
        char a = '%';
        System.out.printf("\nNumber of purchased tickets: %d\nPercentage: %.2f%c\nCurrent income: $%d\nTotal income: $%d\n", countBuyTicket, countBuyTicketInPer, a, cash, tolalCash);
    }
}