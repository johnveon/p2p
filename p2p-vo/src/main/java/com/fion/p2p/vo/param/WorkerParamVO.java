package com.fion.p2p.vo.param;

public class WorkerParamVO {
	
	private String workerClass;
	private String method;
	private String param;
	public String getWorkerClass() {
		return workerClass;
	}
	public void setWorkerClass(String workerClass) {
		this.workerClass = workerClass;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	@Override
	public String toString() {
		return "WorkerParamVO [workerClass=" + workerClass + ", method="
				+ method + ", param=" + param + "]";
	}
	public WorkerParamVO(){
		
	}
	public WorkerParamVO(String workerClass, String method, String param) {
		super();
		this.workerClass = workerClass;
		this.method = method;
		this.param = param;
	}
	
	
}
