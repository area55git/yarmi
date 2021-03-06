package com.doodream.rmovjs.sdp;

import com.doodream.rmovjs.model.RMIServiceInfo;
import com.doodream.rmovjs.serde.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 *  this class is counter implementation of SimpleServiceAdvertiser, which is intended to be used for testing purpose as well
 *
 *  listen broadcast message and try to convert the message into RMIServiceInfo.
 *  if there is service broadcast matched to the target service info, then invoke onDiscovered callback of listener
 */
public class SimpleServiceDiscovery extends BaseServiceDiscovery {

    private DatagramSocket serviceBroadcastSocket;

    public SimpleServiceDiscovery() throws IOException {
        super(100L, TimeUnit.MILLISECONDS);
        serviceBroadcastSocket = new DatagramSocket(SimpleServiceAdvertiser.BROADCAST_PORT);
        serviceBroadcastSocket.setBroadcast(true);
    }

    @Override
    protected RMIServiceInfo receiveServiceInfo(Converter converter) throws IOException {
        byte[] buffer = new byte[64 * 1024];
        Arrays.fill(buffer, (byte) 0);
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        serviceBroadcastSocket.receive(packet);
        return converter.invert(packet.getData(), RMIServiceInfo.class);
    }

    @Override
    protected void close() {
        if(serviceBroadcastSocket == null) {
            return;
        }
        if(serviceBroadcastSocket.isClosed()) {
            return;
        }
        serviceBroadcastSocket.close();
    }
}
