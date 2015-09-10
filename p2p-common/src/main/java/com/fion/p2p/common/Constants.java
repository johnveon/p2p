package com.fion.p2p.common;

import com.alibaba.fastjson.JSON;

/**
 * 常量类
 * 
 * @author zhoufe 2015-9-9 下午11:16:16
 * 
 */
public class Constants {
	// worker 调试开关
	public static final String V = "-v";
	// 配置文件的属性键
	public static final String BORKER_ADDRESS = "borker.address";
	// 主库标识
	public static final String MASTER = "master";
	// 从库标识
	public static final String SLAVE = "slave";
	// 日期格式标识
	public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	// 冒号标识
	public static final String COLON = ":";
	// redis过期前缀标识
	private static final String EXPIRD = "expird";
	// redis不过期前缀标识
	private static final String UNEXPIRD = "unexpird";

	/**
	 * worker 标识key
	 * 
	 * @author zhoufe 2015-9-9 下午11:15:27
	 * 
	 */
	public static final class WorkerAndMethod {
		// worker标识
		public static final String TESTWORKER = "com.fion.p2p.worker.test.TestWorker";
		// worker方法标识
		public static final String TESTWORKER_GETBYID = "com.fion.p2p.worker.test.TestWorker.getById";
		// worker方法标识
		public static final String TESTWORKER_ADD = "com.fion.p2p.worker.test.TestWorker.add";
		// worker方法标识
		public static final String TESTWORKER_DElBYID = "com.fion.p2p.worker.test.TestWorker.delById";
		// worker方法标识
		public static final String TESTWORKER_UPDATEBYID = "com.fion.p2p.worker.test.TestWorker.updateById";

	}

	/**
	 * Map<String,String>里的key值
	 * 
	 * @author zhoufe 2015-9-9 下午11:07:17
	 * 
	 */
	public static final class MapKey {
		//testVO标识
		public static final String TESTVO = "testVO";
		//成功文字标识
		public static final String SUCCESS_TEXT = "成功";
		//成功标识
		public static final String SUCCESS = "success";
		//失败文字标识
		public static final String FAIL_TEXT = "失败";
		//失败标识
		public static final String FAIL = "fail";
		//id标识
		public static final String ID = "id";

	}

	/**
	 * 获取以:号分割的类路径名+id 形成过期的redis key
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 * @author zhoufe 2015-9-9 下午11:14:23
	 */
	public static String EXPIRD_KEY(Class<?> clazz, String id) {
		String className = getClassName(clazz);
		return Constants.EXPIRD + ":" + className + ":" + id;
	}

	/**
	 * 获取以:号分割的类路径名+id 形成不过期的redis key
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 * @author zhoufe 2015-9-9 下午11:14:45
	 */
	public static String UN_EXPIRD_KEY(Class<?> clazz, String id) {
		String className = getClassName(clazz);
		return Constants.UNEXPIRD + ":" + className + ":" + id;
	}

	/**
	 * 获取以:号分割的类路径
	 * 
	 * @param clazz
	 * @return
	 * @author zhoufe 2015-9-9 下午11:15:03
	 */
	private static String getClassName(Class<?> clazz) {
		String className = clazz.getName();
		className = className.replaceAll("\\.", ":");
		return className;
	}

	/**
	 * 获取对象转成json string
	 * 
	 * @param vo
	 * @return
	 * @author zhoufe 2015-9-9 下午11:15:13
	 */
	public static String getJSONString(Object vo) {
		return JSON.toJSONStringWithDateFormat(vo, Constants.DATEFORMAT);
	}

}
