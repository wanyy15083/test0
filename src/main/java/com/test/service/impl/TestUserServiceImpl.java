package com.test.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entity.TestUser;
import com.test.mapper.TestUserMapper;
import com.test.service.TestUserService;

@Service("testUserService")
public class TestUserServiceImpl implements TestUserService {
	private static final Logger logger = Logger
			.getLogger(TestUserServiceImpl.class);

	@Autowired
	private TestUserMapper testUserMapper;

	public void findUser() {
		Integer id = 1;
		TestUser testUser = testUserMapper.selectByPrimaryKey(id);
		logger.info("user.name:" + testUser.getName() + "user.address"
				+ testUser.getAddress());
	}

	public void insertUser() {
		TestUser testUser = new TestUser();
		testUser.setId(null);
		testUser.setName("小明");
		testUser.setAge(18);
		testUser.setAddress("北京");
		testUser.setTelephone("134XXXXXXXXXX");
		testUserMapper.insert(testUser);
		logger.info("user.insert.success");
		
		System.out.println("111");
	}
}
