package com.fion.p2p.vo.test;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class TestVO {
    /**
	 * 
	 */

	private Integer testId;

    private String name;
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
    private Date createDate;
    
    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public TestVO() {
	}
    
	public TestVO(Integer testId, String name, Date createDate) {
		super();
		this.testId = testId;
		this.name = name;
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "TestVO [testId=" + testId + ", name=" + name + ", createDate="
				+ createDate + "]";
	}
}