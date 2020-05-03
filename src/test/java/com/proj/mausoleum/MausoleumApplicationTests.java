package com.proj.mausoleum;

import com.proj.mausoleum.dao.AuditDao;
import com.proj.mausoleum.service.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MausoleumApplicationTests {

	@Autowired
    AuditDao auditDao;

	@Autowired
	AuditService auditService;

	@Test
	void contextLoads() {

	}

}
