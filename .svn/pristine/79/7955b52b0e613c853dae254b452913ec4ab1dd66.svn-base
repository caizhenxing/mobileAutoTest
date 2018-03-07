package com.bmtc.device.domain;

import java.io.Serializable;

/**
 * @author: Jason.ma
 * @date: 2018年1月5日上午9:07:01
 *
 */
public class Response<E> implements Serializable{
	private static final long serialVersionUID = 9122633747274068561L;
	private String code;
	private E data;
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "Response [code=" + code + ", data=" + data + ", msg=" + msg
				+ "]";
	}
}
