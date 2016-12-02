package com.tumei.web;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

	@Test
	public void contextLoads() {
        stringRedisTemplate.opsForValue().set("TEST_1", "abc");

        Assert.assertEquals("abc", stringRedisTemplate.opsForValue().get("TEST_1"));
	}

}
