package com.fion.p2p.common;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 常量类
 * 
 * @author zhoufe 2015-9-9 下午11:16:16
 * 
 */
public class Constants {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Constants.class);

	// worker 调试开关
	public static final String V = "-v";
	// 配置文件的属性键
	public static final String BORKER_ADDRESS = "borker.address";
	public static final String BORKER_ADDRESS_BACKUP = "broker.address.backup";
	// 主库标识
	public static final String MASTER = "master";
	// 从库标识
	public static final String SLAVE = "slave";
	// 日期格式标识
	public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	// 冒号标识
	public static final String COLON = ":";
	//点号标识
	public static final String DOT = ".";
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
		/**
		 * Logger for this class
		 */
		//private static final Logger logger = LoggerFactory.getLogger(WorkerAndMethod.class);

		// worker标识
		public static final String TESTWORKER = "com.fion.p2p.worker.test.TestWorker" + DOT;
		// worker方法标识
		public static final String TESTWORKER_GETBYID = TESTWORKER + "getById";
		// worker方法标识
		public static final String TESTWORKER_ADD = TESTWORKER + "add";
		// worker方法标识
		public static final String TESTWORKER_DElBYID = TESTWORKER + "delById";
		// worker方法标识
		public static final String TESTWORKER_UPDATEBYID = TESTWORKER + "updateById";

	}

	/**
	 * Map<String,String>里的key值
	 * 
	 * @author zhoufe 2015-9-9 下午11:07:17
	 * 
	 */
	public static final class MapKey {
		/**
		 * Logger for this class
		 */
		//private static final Logger logger = LoggerFactory.getLogger(MapKey.class);

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
	
	
	public static String getAliveBroker() {
		//1.用默认地址先telnet
		String retBroker = null;
		String defaultBroker = (String)PropTool.getContextProperty(Constants.BORKER_ADDRESS);
		boolean isAlive= checkAliveBroker(defaultBroker);
		if(isAlive){
			retBroker = defaultBroker;
		}else{
			//2.如果telnet不通
			//3.从备选的borker地址选一个telnet通的
			Set<String> keySet = PropTool.getContextPropertyKeySet();
			for(String brokerKey : keySet){
				if(brokerKey.startsWith(Constants.BORKER_ADDRESS_BACKUP)){
					String checkBroker = (String)PropTool.getContextProperty(brokerKey);
					boolean isAliveTmp= checkAliveBroker(checkBroker);
					if(isAliveTmp){
						retBroker = checkBroker;
						break;
					}
				}
			}
		}
		return retBroker;
	}

	public static boolean checkAliveBroker(String broker) {

		boolean isAlive = false;
		//默认地址
		//String broker = "localhost:6379";//(String)PropTool.getContextProperty(Constants.BORKER_ADDRESS);
		if(broker != null && !"".equals(broker)){
			String[] hostPort = broker.split(":");
			if(hostPort != null && hostPort.length == 3){
				String host = hostPort[1];
				host = host.replaceAll("//", "");
				String port = hostPort[2];
				Socket socket = null;
				try {
					socket = new Socket(host, Integer.parseInt(port));
					isAlive = true;
				} catch (NumberFormatException e) {
					logger.info(broker+"地址telnet端口不通！");
					e.printStackTrace();
				} catch (UnknownHostException e) {
					logger.info(broker+"%s地址telnet主机不通！");
					e.printStackTrace();
				} catch (IOException e) {
					logger.info(broker+"地址telnet不通！");
					e.printStackTrace();
				}finally{
					if(socket != null){
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}else{
				logger.info(broker+"borker地址格式不正确！");
			}
		}else{
			logger.info("borker地址不能为空！");
		}
		return isAlive;
	}

	public static void main(String[] args) {
		System.out.println(checkAliveBroker("localhost:1111"));
	}
}
