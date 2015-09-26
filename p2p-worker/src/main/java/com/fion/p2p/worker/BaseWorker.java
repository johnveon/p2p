package com.fion.p2p.worker;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.zeromq.MdWrkApi;
import org.zeromq.ZMsg;

import com.alibaba.fastjson.JSON;
import com.fion.p2p.common.Constants;
import com.fion.p2p.common.PropTool;
import com.fion.p2p.common.Constants.MapKey;

//@Component
public abstract class BaseWorker {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(BaseWorker.class);

	public BaseWorker(final String serviceName){

		final String BORKER_ADDRESS = (String)PropTool.getContextProperty(Constants.BORKER_ADDRESS);
		logger.info("启动-->" + BORKER_ADDRESS + ":" + serviceName);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				MdWrkApi workerSession = new MdWrkApi(BORKER_ADDRESS,serviceName, true);
				ZMsg reply = null;
				// 返回操作结构
				while (!Thread.currentThread().isInterrupted()) {
					Map<String, String> retParameter = null;
					try {
							ZMsg request = workerSession.receive(reply);
							if (request == null) {
								break; // Interrupted
							}
							
							reply = request; // Echo is complex :-)
							
							String methodName = new String(request.getFirst().getData());
							String parameterStr = new String(request.getLast().getData());
							
							logger.info("调用-->"+serviceName);
							logger.info("方法-->"+methodName);
							logger.info("参数-->"+parameterStr);
							
							@SuppressWarnings("unchecked")
							Map<String, String> parameter = (Map<String, String>)JSON.parseObject(parameterStr, Map.class);
							
							retParameter = runWorker(methodName, parameter);
							//add by fion
							request.destroy();
					}catch (Exception e) {
						e.printStackTrace();
						retParameter = new HashMap<String, String>();
						retParameter.put(MapKey.FAIL,MapKey.FAIL_TEXT);
					}
					String ret = JSON.toJSONString(retParameter);
					reply.add(ret);
				}
				workerSession.destroy();
			}
		},serviceName).start();
		//logger.info("启动完成-->" + BORKER_ADDRESS + ":" + serviceName);
	}
	
	public abstract  Map<String, String> runWorker(String methodName,Map<String, String> parameter);

}
