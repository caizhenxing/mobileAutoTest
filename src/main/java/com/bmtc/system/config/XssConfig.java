package com.bmtc.system.config;


//@Configuration
//public class XssConfig{
//
//	/**
//	 * xss过滤拦截器
//	 */
//	@Bean
//	public FilterRegistrationBean xssFilterRegistrationBean() {
//		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//		filterRegistrationBean.setFilter(new XssFilter());
//		filterRegistrationBean.setOrder(1);
//		filterRegistrationBean.setEnabled(true);
//		filterRegistrationBean.addUrlPatterns("/*");
//		Map<String, String> initParameters = Maps.newHashMap();
//		initParameters.put("excludes", "/favicon.ico,/img/*,/js/*,/css/*");
//		initParameters.put("isIncludeRichText", "true");
//		filterRegistrationBean.setInitParameters(initParameters);
//		return filterRegistrationBean;
//	}
//}
