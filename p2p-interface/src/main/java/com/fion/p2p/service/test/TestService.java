package com.fion.p2p.service.test;

import com.fion.p2p.vo.test.TestVO;

public interface TestService {

	abstract void add(TestVO testVO);

	abstract TestVO getById(Integer id);

	abstract void delById(Integer id);

	abstract void updateById(TestVO testVO);

}
