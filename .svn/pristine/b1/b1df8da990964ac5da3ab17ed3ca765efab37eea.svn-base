package com.bmtc.device.utils;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.NoSuchElementException;

/**
 *@author: Jason.ma
 *@date: 2018年1月2日上午10:16:09
 *
 */
public class AvailablePortFinder {

	public static final int MIN_PORT_NUMBER = 40000;
	public static final int MAX_PORT_NUMBER = 60000;
	private static int current = MIN_PORT_NUMBER;

	private AvailablePortFinder() {

	}

	public synchronized static int getNextAvailable() {
		if (++current == MAX_PORT_NUMBER) {
			current = MIN_PORT_NUMBER;
		}
		return getNextAvailable(current);
	}

	public synchronized static int getNextAvailable(int fromPort) {
		if ((fromPort < MIN_PORT_NUMBER) || (fromPort > MAX_PORT_NUMBER)) {
			throw new IllegalArgumentException("Invalid start port: "
					+ fromPort);
		}

		for (int i = fromPort; i <= MAX_PORT_NUMBER; i++) {
			if (available(i)) {
				return i;
			}
			current++;
		}
		throw new NoSuchElementException(
				"Could not find an available port above" + fromPort);
	}

	private static boolean available(int port) {
		if ((port < MIN_PORT_NUMBER) || (port > MAX_PORT_NUMBER)) {
			throw new IllegalArgumentException("Invalid start port: " + port);
		}

		ServerSocket ss = null;
		DatagramSocket ds = null;

		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {

		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		return false;
	}
}