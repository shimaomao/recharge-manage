package com.dliberty.recharge.app.util;

import com.dliberty.recharge.common.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


@Component
public class GeneratorCardInfoUtil {

    private static GeneratorCardInfoUtil generatorCardInfoUtil;

    @Autowired
    private RedisClient redisClient;

    @PostConstruct
    public void init(){
        generatorCardInfoUtil = this;
        generatorCardInfoUtil.redisClient = this.redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }


    private static final String[] NUMBERS = {"0" , "1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" };
    private static final String DATA_FORMAT = "yyyyMMddHHmmss";
    private static final String REDIS_KEY = "recharge_card_no_suffix";

    public static String getCardNo(String money){
        StringBuffer sb = new StringBuffer();
        sb.append(ConfigUtil.getString("CARD_NO_PREFIX" , "402"));

        //金额
        String moneyStr = getNumberStr(money);
        sb.append(moneyStr);

        //时间
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT);
        String now = sdf.format(new Date());
        String date = now.substring(2);
        sb.append(date);


        long suffix = generatorCardInfoUtil.redisClient.incr(REDIS_KEY, 1);
        if(suffix >= 10000L){
            suffix = 1;
            generatorCardInfoUtil.redisClient.set(REDIS_KEY, 0);
        }

        String str = getNumberStr(String.valueOf(suffix));
        sb.append(str);

        return sb.toString();
    }

    public static String getSecretKey(String cardNo){
        StringBuffer sb = new StringBuffer();
        int hashCode = cardNo.hashCode();
        if(hashCode < 0){
            hashCode = -hashCode;
        }
        String temp = String.valueOf(hashCode);
        sb.append(temp);

        //随机码
        int length = Integer.valueOf(ConfigUtil.getString("secret_key_length" , "12"));
        sb.append(randomNumber((length >=10?length:10) - temp.length()));

        return sb.toString();
    }

    /*public static String getSecretKey(String money){
        StringBuffer sb = new StringBuffer();
        sb.append(SECRET_KEY_PREFIX);

        //金额
        String moneyStr = getNumberStr(money);
        sb.append(moneyStr);

        //随机码
        sb.append(randomNumber(12));

        return sb.toString();
    }*/

    /**
     * 随机生成数字字符串
     * @param length 字符串长度
     * @return
     */
    public static String randomNumber(int length){
        if(length <= 0){
            return "";
        }
        StringBuffer sb =  new StringBuffer();
        for(int i = 1 ; i <= length ; i++){
            Random random = new Random();
            sb.append(NUMBERS[random.nextInt(10)]);
        }
        return sb.toString();
    }

    /**
     * 得到四位数的字符串
     * @param number
     * @return
     */
    private static String getNumberStr(String number){
        String prefix = "";
        switch (number.length()){
            case 1:
                prefix =  "000";
                break;
            case 2:
                prefix = "00";
                break;
            case 3:
                prefix = "0" ;
                break;
            default:
                break;
        }
        return prefix + number;
    }
}
