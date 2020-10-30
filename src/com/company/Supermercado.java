package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Supermercado {

    private int numeroCajas;
    private boolean[] cajas;
    private Semaphore semaphore;
    private ReentrantLock reentrantLock = new ReentrantLock(true);
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private Date date;

    public Supermercado(int numeroCajas) {
        this.numeroCajas = numeroCajas;
        cajas = new boolean[numeroCajas];
        semaphore = new Semaphore(numeroCajas, true);
        for (int i = 0; i < numeroCajas; i++) {
            cajas[i] = true;
        }
    }

    public void goToCaja() throws InterruptedException {
        semaphore.acquire();
        try {
            int caja = selectCaja();
            if (caja>=0){
                pasarPorCaja(caja);
                terminarCompra(caja);
            }
        } finally {
            semaphore.release();
        }
    }

    private int selectCaja() {
        reentrantLock.lock();
        try {
            for (int i = 0; i < numeroCajas; i++) {
                if (cajas[i]){
                    cajas[i] = false;
                    return i;
                }
            }
            return -1;
        } finally {
            reentrantLock.unlock();
        }
    }

    private void pasarPorCaja(int caja) throws InterruptedException {
        date = new Date();
        System.out.printf("El cliente %s está pasando por la caja %d a las %s\n", Thread.currentThread().getName(), caja + 1, dateFormat.format(date));
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(4) + 1);
    }

    private void terminarCompra(int caja) {
        date = new Date();
        System.out.printf("El cliente %s ha terminado de comprar en la caja %d a las %s\n", Thread.currentThread().getName(), caja + 1, dateFormat.format(date));
        cajas[caja] = true;
    }


}
