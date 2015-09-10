package com.fion.p2p.worker.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fion.p2p.common.Constants;
import com.fion.p2p.common.Constants.MapKey;
import com.fion.p2p.common.Constants.WorkerAndMethod;
import com.fion.p2p.service.test.TestService;
import com.fion.p2p.vo.test.TestVO;
import com.fion.p2p.worker.BaseWorker;
import com.fion.redis.RedisTool;

/**
 * Majordomo Protocol worker example. Uses the mdwrk API to hide all MDP aspects
 * 
 */
@Component
public class TestWorker extends BaseWorker{

	@Autowired
	private TestService testService;
	@Autowired
	private RedisTool redisTool;

	public TestWorker(){
		super(WorkerAndMethod.TESTWORKER);
	}
	
	/**
	 * 等待处理的worker
	 */
	@Override
	public Map<String, String> runWorker(String methodName,
			Map<String, String> parameter) {
		
		Map<String, String> retParamter = new HashMap<String, String>();
		retParamter.put(MapKey.SUCCESS, MapKey.SUCCESS_TEXT);
		
		String id = null;
		TestVO testVO = null;
		String testVOStr = null;
		switch (methodName) {
			case WorkerAndMethod.TESTWORKER_GETBYID:
				id = (String)parameter.get(MapKey.ID);
				getById(id);
				break;
			case WorkerAndMethod.TESTWORKER_DElBYID:
				id = (String)parameter.get(MapKey.ID);
				delById(id);
				break;
			case WorkerAndMethod.TESTWORKER_ADD:
				testVOStr = (String)parameter.get(MapKey.TESTVO);
				testVO = JSON.parseObject(testVOStr, TestVO.class);
				
				add(testVO);
				
				//把存入数据库的对象String返回
				testVOStr = Constants.getJSONString(testVO);
				retParamter.put(MapKey.TESTVO, testVOStr);
				
				break;
			case WorkerAndMethod.TESTWORKER_UPDATEBYID:
				testVOStr = (String)parameter.get(MapKey.TESTVO);
				testVO = JSON.parseObject(testVOStr, TestVO.class);
				updateById(testVO);
				break;
				
	
			default:
				break;
		}
		return retParamter;
	}

	/**
	 * 增加
	 * @param testVO
	 * @author zhoufe 2015-9-10 下午9:08:55
	 */
	private void add(TestVO testVO) {

		testService.add(testVO);
		
		String key = Constants.UN_EXPIRD_KEY(TestVO.class, String.valueOf(testVO.getTestId()));
		redisTool.del(key);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 * @author zhoufe 2015-9-10 下午9:09:05
	 */
	private TestVO getById(String id) {

		TestVO testVO = testService.getById(Integer.parseInt(id));
		
		if(testVO != null){
			String key = Constants.UN_EXPIRD_KEY(TestVO.class,String.valueOf(id));
			String testVOStr = Constants.getJSONString(testVO);
			redisTool.set(key, testVOStr);
		}
		return testVO;
	}

	/**
	 * 删除
	 * @param id
	 * @author zhoufe 2015-9-10 下午9:09:18
	 */
	private void delById(String id) {
		testService.delById(Integer.parseInt(id));
		String key = Constants.UN_EXPIRD_KEY(TestVO.class, String.valueOf(id));
		redisTool.del(key);
		
	}

	/**
	 * 更新
	 * @param testVO
	 * @author zhoufe 2015-9-10 下午9:09:31
	 */
	private void updateById(TestVO testVO) {
		testService.updateById(testVO);
		String key = Constants.UN_EXPIRD_KEY(TestVO.class, String.valueOf(testVO.getTestId()));
		redisTool.del(key);
	}
}
