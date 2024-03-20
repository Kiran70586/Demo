package com.smart.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class Message {
	
	private String content;
	private  String type;
	
	public Message() {
		super();
	}

	public Message(String content, String type) {
		super();
		this.content = content;
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + ", type=" + type + "]";
	}
	
	public void removeattribute1()
	{
		try
		{
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            session.removeAttribute("message");
			
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	
}
