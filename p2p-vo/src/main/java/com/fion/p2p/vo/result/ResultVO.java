package com.fion.p2p.vo.result;

public class ResultVO {

	
	private String status;
	private String msg;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public ResultVO(){
		
	}
	public ResultVO(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "ResultVO [status=" + status + ", msg=" + msg + "]";
	}
	
	
	
}
