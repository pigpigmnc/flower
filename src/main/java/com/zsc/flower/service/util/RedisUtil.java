package com.zsc.flower.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Component
public class RedisUtil {
    private JedisPool jedisPool;
    @Autowired
    JwtUtil jwtUtil;
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
    public RedisUtil(){
        jedisPool=new JedisPool();
    }
    //添加
    public void set(String key, String value){
        Jedis jedis = this.jedisPool.getResource();
        jedis.set(key, value);
        jedis.close();
    }


    //添加，带超时时间
    public void setex(String key, int seconds, String value){
        Jedis jedis = this.jedisPool.getResource();
        jedis.setex(key, seconds, value);
        jedis.close();
    }

    //获取
    public String getRedisValue(String key){
        Jedis jedis = this.jedisPool.getResource();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }


    //查看某个键是否存在
    public boolean exists(String key){
        Jedis jedis = this.jedisPool.getResource();
        Boolean exists = jedis.exists(key);
        jedis.close();
        return exists;
    }

    //查看超时时间
    public Long ttl(String key){
        Jedis jedis = this.jedisPool.getResource();
        Long ttl = jedis.ttl(key);
        jedis.close();
        return ttl;
    }

    //删除
    public void del(String key){
        Jedis jedis = this.jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }


}
