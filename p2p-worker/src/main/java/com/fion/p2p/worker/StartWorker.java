package com.fion.p2p.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartWorker {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(StartWorker.class);

	/**
	 * @param args
	 */
	@SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args) {
			logger.info("准备清点Worker，请稍等...");
			
			AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(new String []{"spring.xml"});
			
			logger.info("已清点完Worker！");
	}

}
