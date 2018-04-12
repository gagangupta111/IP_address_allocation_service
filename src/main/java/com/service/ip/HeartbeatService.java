package com.service.ip;

public interface HeartbeatService {

    public Boolean refresh(String macAddress, String allocatedIPAddr);

}