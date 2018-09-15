package com.github.ffalcinelli.jdivert;

import com.github.ffalcinelli.jdivert.exceptions.WinDivertException;

public class Main {

	public static void main(String[] args) throws Exception {

		// Capture only TCP packets to port 80, i.e. HTTP requests.
		WinDivert w = new WinDivert("tcp.DstPort == 8010 or tcp.DstPort == 8012");

		w.open(); // packets will be captured from now on

		for (int i = 0; i < 1000; i++) {
			Packet packet = w.recv(); // read a single packet
			//System.out.println("recv:" + packet);
			if (packet.getDstPort() == 8010) {
				packet.setDstPort(8011);
				System.out.println(new String(packet.getPayload()));
				if(packet.getPayload().length>0) {
					packet.setPayload("hahaha".getBytes());					
				}
			} else if (packet.getDstPort() == 8012) {
				packet.setSrcPort(8010);
			}
			System.out.println("send:" + packet);
			w.send(packet); // re-inject the packet into the network stack
		}

		w.close(); // stop capturing packets
	}
}
