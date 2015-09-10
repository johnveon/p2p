package com.fion.p2p.service.test.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fion.p2p.dao.test.TestDAO;
import com.fion.p2p.service.test.TestService;
import com.fion.p2p.vo.test.TestVO;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDAO testDAO;
	
	public void add(TestVO testVO) {
		testDAO.insertSelective(testVO);
	}

	public TestVO getById(Integer id) {
		TestVO testVO = testDAO.selectByPrimaryKey(id);
		return testVO;
	}

	@Override
	public void delById(Integer id) {
		testDAO.deleteByPrimaryKey(id);
	}

	@Override
	public void updateById(TestVO testVO) {
		testDAO.updateByPrimaryKeySelective(testVO);
	}

}
