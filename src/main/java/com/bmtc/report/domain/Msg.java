package com.bmtc.report.domain;

import java.io.Serializable;

public class Msg implements Serializable{
	
	private String level;
	
    private String content;
    
    private String timestamp;
    
    private String html;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Override
	public String toString() {
		return "Msg [level=" + level + ", content=" + content + ", timestamp=" + timestamp + ", html=" + html + "]";
	}
    
    
}
