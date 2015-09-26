package com.fion.p2p.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 此类即完成了PropertyPlaceholderConfigurer的任务， 同时又提供了上下文properties访问的功能。
 * 于是在Spring配置文件中把PropertyPlaceholderConfigurer改成PropertyPlaceholderConfigurerUtil
 */
public class PropTool extends
		PropertyPlaceholderConfigurer {
	private static final Logger logger = LoggerFactory
			.getLogger(PropTool.class);
	private static Map<String, Object> ctxPropertiesMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);
		// load properties to ctxPropertiesMap
		ctxPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
		logger.debug("开始打印系统环境变量和系统参数");
		//MySystemPropertiesUtil.getSystemEnvsAndProperties();
	}

	// static method for accessing context properties
	public static Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}
	
	public static Set<String> getContextPropertyKeySet() {
		return ctxPropertiesMap.keySet();
	}
}