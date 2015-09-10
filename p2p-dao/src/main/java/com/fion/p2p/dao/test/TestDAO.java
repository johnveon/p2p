package com.fion.p2p.dao.test;

import com.fion.p2p.common.Constants;
import com.fion.p2p.common.dynamicdatadsource.DataSource;
import com.fion.p2p.vo.test.TestVO;

public interface TestDAO {
	
	@DataSource(Constants.MASTER)
    int deleteByPrimaryKey(Integer testId);

	@DataSource(Constants.MASTER)
    int insert(TestVO record);
	
	@DataSource(Constants.MASTER)
    int insertSelective(TestVO record);

	@DataSource(Constants.SLAVE)
    TestVO selectByPrimaryKey(Integer testId);
	
	@DataSource(Constants.MASTER)
    int updateByPrimaryKeySelective(TestVO record);
	
	@DataSource(Constants.MASTER)
    int updateByPrimaryKey(TestVO record);
}