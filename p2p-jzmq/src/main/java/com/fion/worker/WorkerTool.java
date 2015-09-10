package com.fion.worker;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.zeromq.MdCliApi;
import org.zeromq.ZMsg;

import com.alibaba.fastjson.JSON;
import com.fion.p2p.common.Constants;
import com.fion.p2p.common.PropTool;

@Component
public class WorkerTool {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(WorkerTool.class);

	/**
	 * 调用worker
	 * @param workerClass
	 * @param method
	 * @param parameter
	 * @return
	 * @author zhoufe 2015-9-10 下午9:23:14
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> callWorker(String workerClass,String method,Map<String, String> parameter){
		
		Map<String, String> retParameter = null;
		
		String BORKER_ADDRESS = (String)PropTool.getContextProperty(Constants.BORKER_ADDRESS);
        MdCliApi clientSession = new MdCliApi(BORKER_ADDRESS, false);
        ZMsg request = new ZMsg();
        logger.info("调用-->"+method);
        
        request.add(JSON.toJSONStringWithDateFormat(parameter,Constants.DATEFORMAT));

        ZMsg reply = clientSession.send(workerClass,method, request);
        
        if (reply != null){
        	
        	String retParameterStr = new String(reply.getLast().getData());
        	logger.info("worker返回-->"+retParameterStr);
        	retParameter =(Map<String, String>) JSON.parseObject(retParameterStr,Map.class);
        	
            reply.destroy();
        }
        
		return retParameter;
	}
}
