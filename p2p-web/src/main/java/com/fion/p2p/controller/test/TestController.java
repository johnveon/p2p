package com.fion.p2p.controller.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fion.p2p.common.Constants;
import com.fion.p2p.common.Constants.MapKey;
import com.fion.p2p.common.Constants.WorkerAndMethod;
import com.fion.p2p.vo.test.TestVO;
import com.fion.redis.RedisTool;
import com.fion.worker.WorkerTool;

@Controller
@RequestMapping("/testController")
public class TestController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private WorkerTool workerTool;
	
	@Autowired
	private RedisTool redisTool;
	
	
	@RequestMapping(method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String defaultTestController(){
		logger.info("@RequestMapping没有value=\"\"，默认进入的方法！");
		return null;
	}
	
	/**
	 * 获取testvo信息，根据id
	 * @return
	 * @author zhoufe 2015-9-9 下午11:28:02
	 */
	@RequestMapping(value="/getById",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String getById(){
		
		String id = "1";

		String testInfo = getByIdCallWorker(id);
		
		logger.info("testInfo="+testInfo);
		
		return testInfo;
	}

	/**
	 * 调用worker
	 * @param id
	 * @return
	 * @author zhoufe 2015-9-9 下午11:28:39
	 */
	private String getByIdCallWorker(String id) {
		Map<String, String> paramter = new HashMap<String, String>();
		paramter.put(MapKey.ID, id);
		String redisKey = Constants.UN_EXPIRD_KEY(TestVO.class, id);
		String testInfo = redisTool.get(redisKey);
		//testInfo = null;
		if(testInfo == null ){
			Map<String, String> ret =  workerTool.callWorker(WorkerAndMethod.TESTWORKER, WorkerAndMethod.TESTWORKER_GETBYID,paramter);
			if(ret != null 
					&& MapKey.SUCCESS.equals((ret.containsKey(MapKey.SUCCESS)))){
				testInfo = redisTool.get(redisKey);
			}
		}
		return testInfo;
	}
	
	/**
	 * 删除信息，根据id
	 * @return
	 * @author zhoufe 2015-9-9 下午11:29:56
	 */
	@RequestMapping(value="/delById",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String delById(){
		Map<String, String> paramter = new HashMap<String, String>();
		String id = "1";
		paramter.put(MapKey.ID, id);
		Map<String, String> ret = workerTool.callWorker(WorkerAndMethod.TESTWORKER, WorkerAndMethod.TESTWORKER_DElBYID,paramter);
		logger.info("ret="+ret);
		
		return null;
	}
	
	/**
	 * 增加信息，根据id
	 * @return
	 * @author zhoufe 2015-9-9 下午11:30:22
	 */
	@RequestMapping(value="/add",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String add(){
		TestVO testVO = new TestVO(null,"fion",new Date());
		String testVOStr = JSON.toJSONStringWithDateFormat(testVO, Constants.DATEFORMAT);
		Map<String, String> paramter = new HashMap<String, String>();
		paramter.put(MapKey.TESTVO, testVOStr);
		Map<String, String> ret = workerTool.callWorker(WorkerAndMethod.TESTWORKER, WorkerAndMethod.TESTWORKER_ADD,paramter);
		
		if(ret != null && ret.containsKey(MapKey.SUCCESS)){
			//返回带数据库id的对象string
			testVOStr = ret.get(MapKey.TESTVO);
			TestVO retTestVO = JSON.parseObject(testVOStr, TestVO.class);
			logger.info(retTestVO.toString());
		}
		
		logger.info("ret="+ret);
		
		return null;
	}
	
	/**
	 * 修改信息，根据id
	 * @return
	 * @author zhoufe 2015-9-9 下午11:30:36
	 */
	@RequestMapping(value="/updateById",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String updateById(){
		TestVO testVO = new TestVO();
		testVO.setTestId(1);
		//设置修改的值
		testVO.setName("吃饭");
		//vo转换成string
		String testVOStr = JSON.toJSONStringWithDateFormat(testVO, Constants.DATEFORMAT);
		//放入map
		Map<String, String> paramter = new HashMap<String, String>();
		paramter.put(MapKey.TESTVO, testVOStr);
		//调用worker，更新值
		Map<String, String> ret = workerTool.callWorker(WorkerAndMethod.TESTWORKER, WorkerAndMethod.TESTWORKER_UPDATEBYID,paramter);
		logger.info("ret="+ret);
		return null;
	}
}
