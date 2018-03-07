package com.bmtc.device.controller;

import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bmtc.device.domain.Device;
import com.bmtc.device.domain.Response;
import com.bmtc.device.service.DevicesService;



@RequestMapping("/bmtc")
@Controller
public class DeviceManagerController {
	private static final Logger logger = LoggerFactory.getLogger(DeviceManagerController.class);
	
	@Autowired
	
	private DevicesService devicesService;
	
	@RequestMapping(value="/all/android/info", method=RequestMethod.GET)
	@ResponseBody
	public Response<List<Device>> getAndroidInfo(){
		
		Response<List<Device>> deviceVo = new Response<List<Device>>();
		List<Device> deviceInfo = devicesService.getAllAndroidInfo();
		
		if (deviceInfo.size() > 0) {
			deviceVo.setCode("0000");
			deviceVo.setData(deviceInfo);
			deviceVo.setMsg("操作成功");
		}else { 
			deviceVo.setCode("9997");
			deviceVo.setData(null);
			deviceVo.setMsg("未检测到设备");
		}
		logger.debug("返回Android设备信息：{}", deviceVo);
		return deviceVo;
	}
	
	@RequestMapping(value="/all/IOS/info", method=RequestMethod.GET)
	@ResponseBody
	public Response<List<Device>> getIOSInfo(){
		
		Response<List<Device>> deviceVo = new Response<List<Device>>();
		List<Device> deviceInfo = devicesService.getAllIOSInfo();
		
		if (deviceInfo.size() > 0) {
			deviceVo.setCode("0000");
			deviceVo.setData(deviceInfo);
			deviceVo.setMsg("操作成功");
		}else {
			deviceVo.setCode("9997");
			deviceVo.setData(null);
			deviceVo.setMsg("未检测到设备");
		}
		logger.debug("返回IOS设备信息：{}", deviceVo);
		return deviceVo;
	}
}
