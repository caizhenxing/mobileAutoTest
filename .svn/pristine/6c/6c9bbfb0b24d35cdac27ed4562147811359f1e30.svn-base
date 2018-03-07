package com.bmtc.svn.common.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

public class ExtractPathFromPattern {
    protected static Logger logger = Logger.getLogger(ExtractPathFromPattern.class);
	
	//处理@PathVariable带斜杠获取的问题
	@SuppressWarnings("unused")
	public static String extractPathFromPattern(final HttpServletRequest request) {
		logger.info("ExtractPathFromPattern.extractPathFromPattern() start");
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);  
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);  
		logger.info("ExtractPathFromPattern.extractPathFromPattern() start");
		return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);  
	}  
}
