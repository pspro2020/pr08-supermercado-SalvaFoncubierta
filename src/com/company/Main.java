package com.company;

public class Main {

    public static void main(String[] args) {

        final int NUMBEROFCLIENTS = 50;
        Supermercado supermercado = new Supermercado(4);

        for (int i = 0; i < NUMBEROFCLIENTS; i++) {
            new Thread(new Cliente(supermercado)).start();
        }
    }
}
