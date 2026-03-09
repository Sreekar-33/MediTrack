package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static IdGenerator instance;

    private AtomicInteger counter = new AtomicInteger(1);

    private IdGenerator(){}

    public static IdGenerator getInstance(){
        if(instance==null){
            synchronized (IdGenerator.class){
                if(instance==null){
                    instance = new IdGenerator();
                }
            }
        }

        return instance;
    }

    public int generateId(){

        return counter.getAndIncrement();

    }
}
