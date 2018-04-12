package com.pool.ip;

import java.util.Enumeration;

public class IP_Pool extends Abstract_Pool<String> {

    private static volatile IP_Pool instance;

    private IP_Pool(long expirationTime) {

        super(expirationTime);
        for (int i = 0; i < 10; i++) {
            unlocked.add("10.20.30." + i);
        }

    }


    public static IP_Pool getPool(long expirationTime){

        if(instance == null){
            synchronized (IP_Pool.class) {
                if(instance == null){
                    instance = new IP_Pool(expirationTime);
                }
            }
        }
        return instance;

    }

    @Override
    public boolean refresh(String o) {

        long now = System.currentTimeMillis();
        long lockedTime = locked.get(o);

        if ((now - lockedTime) > expirationTime) {

            locked.remove(o);
            unlocked.add(o);
            return false;

        }

        locked.put(o, now);
        return true;

    }

    @Override
    public void expire() {

        long now = System.currentTimeMillis();
        String t;
        if (locked.size() > 0) {

            Enumeration<String> e = locked.keys();
            while (e.hasMoreElements()) {
                t = e.nextElement();
                if ((now - locked.get(t)) > expirationTime) {
                    // object has expired
                    locked.remove(t);
                    unlocked.add(t);
                }

            }
        }
    }

}