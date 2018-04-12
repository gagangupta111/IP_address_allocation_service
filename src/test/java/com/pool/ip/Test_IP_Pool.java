package com.pool.ip;

import com.service.ip.AllocationService;
import com.service.ip.HeartbeatService;
import com.service.ip.ServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import java.util.LinkedHashSet;
import java.util.Set;

public class Test_IP_Pool {

    @Test
    public void test_allocation() {

        ServiceImpl service = new ServiceImpl(500);
        AllocationService allocationService = service;
        HeartbeatService heartbeatService = service;

        Abstract_Pool<String> pool = IP_Pool.getPool(500);
        Set<String> set = new LinkedHashSet<String>();
        Set<String> nonAllocated = new LinkedHashSet<String>();

        String mac_address = null;
        String returnedAddress = null;
        for (int i = 0; i < 20; i++){

            mac_address = "11.22.33." + i;
            set.add(mac_address);
            returnedAddress = allocationService.allocate(mac_address);
            if (returnedAddress == null){
                nonAllocated.add(mac_address);
            }

        }

        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }

        pool.expire();

        for (String set_address : nonAllocated){
            returnedAddress = allocationService.allocate(set_address);
            Assert.assertEquals(null, returnedAddress);
            break;
        }

    }

    @Test
    public void test_allocation_expired() {

        ServiceImpl service = new ServiceImpl(300);
        AllocationService allocationService = service;
        HeartbeatService heartbeatService = service;

        Abstract_Pool<String> pool = IP_Pool.getPool(300);
        Set<String> set = new LinkedHashSet<String>();
        Set<String> nonAllocated = new LinkedHashSet<String>();

        String mac_address = null;
        String returnedAddress = null;
        for (int i = 0; i < 20; i++){

            mac_address = "11.22.33." + i;
            set.add(mac_address);
            returnedAddress = allocationService.allocate(mac_address);
            if (returnedAddress == null){
                nonAllocated.add(mac_address);
            }

        }

        try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

        pool.expire();

        for (String set_address : nonAllocated){
            returnedAddress = allocationService.allocate(set_address);
            Assert.assertEquals(true, returnedAddress!=null);
            break;
        }

    }
}