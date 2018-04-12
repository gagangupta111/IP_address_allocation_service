package com.pool.ip;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

public abstract class Abstract_Pool<T> {

    protected long expirationTime;
    protected Hashtable<T, Long> locked;
    protected HashSet<T> unlocked;

    public Abstract_Pool(long expirationTime) {

        this.expirationTime = expirationTime;// 30 seconds
        locked = new Hashtable<T, Long>();
        unlocked = new HashSet<T>();

    }

    public abstract boolean refresh(T o);

    public abstract void expire();

    public synchronized T checkOut() {

        long now = System.currentTimeMillis();

        T t = null;

        if (unlocked.size() > 0) {

            for (Iterator<T> it = unlocked.iterator(); it.hasNext(); ) {
                t = it.next();
                locked.put(t, now);
                unlocked.remove(t);
                break;
            }

        }

        return t;

    }

    public synchronized void checkIn(T t) {

        locked.remove(t);
        unlocked.add(t);

    }

}
