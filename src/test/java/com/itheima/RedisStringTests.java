package com.itheima;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.redis.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;


/**
 * RedisTemplate的两种序列化实践方案：
 * 方案 2：
 * 1.使用StringRedisTemplate
 * 2.写入redis时，手动把对象序列化为json
 * 3.读取redis时，手动把读取的json反序列化为对象
 */

@SpringBootTest
class RedisStringTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void testString() {
        //写入一条数据
        stringRedisTemplate.opsForValue().set("com", "itheima");
        //获取数据
        String com = stringRedisTemplate.opsForValue().get("com");
        System.out.println("com = " + com);
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSaveUser() throws JsonProcessingException {
        //创建对象
        User user = new User("robot", 35);
        //手动序列化
        String json = mapper.writeValueAsString(user);
        //写入数据
        stringRedisTemplate.opsForValue().set("user:200", json);
        //获取数据
        String jsonUser = stringRedisTemplate.opsForValue().get("user:200");
        //手动反序列化
        User user1 = mapper.readValue(jsonUser, User.class);
        System.out.println("user1 = " + user1);
    }


    @Test
    void testHash(){

        stringRedisTemplate.opsForHash().put("user:300","name","robot");
        stringRedisTemplate.opsForHash().put("user:300","age","26");

        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries("user:300");
        System.out.println("entries = " + entries);
    }

}
