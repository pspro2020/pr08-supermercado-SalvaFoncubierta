package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Cliente implements Runnable{

    private Supermercado supermercado;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private Date date;

    public Cliente(Supermercado supermercado) {
        this.supermercado = supermercado;
    }

    @Override
    public void run() {
        try {
            date = new Date();
            System.out.printf("El cliente %s está yendo al supermercado a las %s\n", Thread.currentThread().getName(), dateFormat.format(date));
            buy();
            supermercado.goToCaja();
        } catch (InterruptedException e) {
            return;
        }
    }

    private void buy() throws InterruptedException {
        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3) + 1);
        date = new Date();
        System.out.printf("El cliente %s se ha puesto en la cola a las %s\n", Thread.currentThread().getName(), dateFormat.format(date));
    }


}
