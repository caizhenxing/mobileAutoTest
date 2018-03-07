package com.bmtc.report.domain;

import java.io.Serializable;
import java.util.List;

public class Kw implements Serializable{
	
	 private String library;
	 
	 private String name;
	 
	 private String type;
	 
	 private List<Kw> kw;
	 
	 private Status status;
	 
	 private List<Msg> msg;

	 private String img;
	 
	 private String log;

	public String getLibrary() {
		return library;
	}

	public void setLibrary(String library) {
		this.library = library;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Kw> getKw() {
		return kw;
	}

	public void setKw(List<Kw> kw) {
		this.kw = kw;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Msg> getMsg() {
		return msg;
	}

	public void setMsg(List<Msg> msg) {
		this.msg = msg;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	@Override
	public String toString() {
		return "Kw [library=" + library + ", name=" + name + ", type=" + type + ", kw=" + kw + ", status=" + status
				+ ", msg=" + msg + ", img=" + img + ", log=" + log + "]";
	}

}
