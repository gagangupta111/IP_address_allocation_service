package com.service.ip;

import com.pool.ip.IP_Pool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImpl implements AllocationService, HeartbeatService{

    private IP_Pool ip_pool;
    private Map<String, String> map = new ConcurrentHashMap<String, String>();

    public ServiceImpl(long expirationTime ) {

        this.ip_pool = IP_Pool.getPool(expirationTime);

    }

    public ServiceImpl() {

        long expirationTime = 30000; // 30 seconds
        this.ip_pool = IP_Pool.getPool(expirationTime);

    }

    public String allocate(String macAddress) {

        String returnedAddress = map.get(macAddress);
        if (returnedAddress != null){

            return returnedAddress;

        }

        String ip = ip_pool.checkOut();
        if (ip != null){
            map.put(macAddress, ip);
        }
        return ip;

    }

    public Boolean refresh(String macAddress, String allocatedIPAddr) {

        String returnedAddress = map.get(macAddress);
        if (returnedAddress == null){
            return false;
        }else if (returnedAddress != allocatedIPAddr){
            return false;
        }

        ip_pool.refresh(allocatedIPAddr);
        return true;

    }
}
