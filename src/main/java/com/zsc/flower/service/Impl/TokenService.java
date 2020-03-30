package com.zsc.flower.service.Impl;

import com.zsc.flower.domain.entity.Users;
import com.zsc.flower.service.util.JsonUtil1;
import com.zsc.flower.service.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zsc.flower.service.util.AuthUtil.getClaimsFromToken;


@Service("tokenService")
public class TokenService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    UsersServiceImpl usersService;
    @Autowired
    private JsonUtil1 jackson;
    public void saveToken(Users user, int time, String token ){
        //如果是PC，那么token保存两个小时；如果是MOBILE
        String id=String.valueOf(user.getId());
        redisUtil.setex(id,time, token);

    }

   /* public void saveOrder(Users user, int time, Orders order)
    {
        String id="order"+String.valueOf(user.getId());
        String orderstr=JsonUtil1.obj2String(order);

        redisUtil.setex(id,time,orderstr);
    }*/
    public boolean exists(String key)
    {
        boolean result=redisUtil.exists(key);
        return result;
    }
    public String getRedisValue(String key)
    {
       String result= redisUtil.getRedisValue(key);
       return result;
    }

    public void del(String key)
    {
        redisUtil.del(key);
    }

    public String reUserIdBytoken(String token)
    {
        String id=getClaimsFromToken(token).getSubject();

        return id;
    }
    public boolean existsToken(String token)
    {
        String userJson=this.reUserIdBytoken(token);
        if(redisUtil.exists(userJson))
        {
            String tvalue=this.getRedisValue(userJson);
            if(token.equals(tvalue))
                return true;
            else
                return false;
        }
        else
        {
            return false;
        }
    }

}
