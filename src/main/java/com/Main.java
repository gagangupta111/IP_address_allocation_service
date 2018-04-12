package com;

import com.pool.ip.Abstract_Pool;
import com.pool.ip.IP_Pool;
import com.service.ip.AllocationService;
import com.service.ip.HeartbeatService;
import com.service.ip.ServiceImpl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args){

        ServiceImpl service = new ServiceImpl(10000);
        AllocationService allocationService = service;
        HeartbeatService heartbeatService = service;

        Abstract_Pool<String> pool = IP_Pool.getPool(10000);

        Set<String> set = new LinkedHashSet<String>();
        String mac_address = "";
        for (int i = 0; i < 20; i++){

            mac_address = "11.22.33." + i;
            set.add(mac_address);
            allocationService.allocate(mac_address);

        }

        try {

            Thread.sleep(1000);

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        System.out.println();
        pool.expire();
        for (String set_address : set){

            String returnedAddress = allocationService.allocate(set_address);
            heartbeatService.refresh(set_address, returnedAddress);

            System.out.println("mac_address:" + set_address + "IP allocated:" + returnedAddress);

        }



    }

}
