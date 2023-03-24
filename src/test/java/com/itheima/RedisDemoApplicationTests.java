package com.itheima;

import com.itheima.redis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * RedisTemplate的两种序列化实践方案：
 * 方案 1：
 * 1.自定义RedisTemplate
 * 2.修改RedisTemplate的序列化器为GenericJackson2JsonRedisSerializer
 */

@SpringBootTest
class RedisDemoApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void testString() {
        //写入一条数据
        redisTemplate.opsForValue().set("com", "heima");
        //获取数据
        redisTemplate.opsForValue().get("com");
    }

    @Test
    void testSaveUser() {
        //写入数据
        redisTemplate.opsForValue().set("user:100", new User("虎哥", 35));
        //自动转成了JSON格式

        //获取数据
        User user = (User) redisTemplate.opsForValue().get("user:100");
        System.out.println("user = " + user);
    }

}
