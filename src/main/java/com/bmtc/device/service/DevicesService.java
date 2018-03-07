package com.bmtc.device.service;

import java.util.List;

import com.bmtc.device.domain.Device;

public interface DevicesService {
	/**
	 * 获取服务器上所有android设备手机信息
	 * @return List<Device> uid，版本， 分辨率，产品名，设备状态 1：繁忙 0：空闲
	 */
	public List<Device> getAllAndroidInfo();
	
	/**
	 * 获取服务器上所有IOS设备手机信息
	 * @return List<Device> uid，版本， 分辨率，产品名，设备状态 1：繁忙 0：空闲
	 */
	public List<Device> getAllIOSInfo();
	
	/**
	 * 通过udid获取设备信息
	 * @param udid  设备唯一标识，pc查看方式 adb devices
	 * @return
	 */
	public Device getAndroidInfoByUdid(String udid);
	
	/**
	 * 通过udid获取设备信息
	 * @param udid 设备唯一标识，pc查看方式 idevice_id -l
	 * @return
	 */
	public Device getIOSInfoByUdid(String udid);
	
}
