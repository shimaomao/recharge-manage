package com.dliberty.recharge.common.utils;

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
    private static final Integer DEFAULT_LENGTH = 4;
    private static final String CARD_NO_PREFIX = "40200";
    private static final String SECRET_KEY_PREFIX = "61900";
    private static final String DATA_FORMAT = "yyyyMMddHHmm";
    private static final String REDIS_KEY = "recharge_card_no_suffix";

    public static String getCardNo(Integer money){
        StringBuffer sb = new StringBuffer();
        sb.append(CARD_NO_PREFIX);

        //金额
        String moneyStr = getNumberStr(Long.valueOf(String.valueOf(money)));
        sb.append(moneyStr);

        //时间
        SimpleDateFormat sdf = new SimpleDateFormat(DATA_FORMAT);
        String now = sdf.format(new Date());
        String date = now.substring(0,6);
        String time = now.substring(8);
        sb.append(date).append(time);

        Integer recharge_card_no_suffix = (Integer) generatorCardInfoUtil.redisClient.get(REDIS_KEY);
        if(recharge_card_no_suffix == null || recharge_card_no_suffix.intValue() >= 9999){
            generatorCardInfoUtil.redisClient.set(REDIS_KEY , 0);
        }
        String str = getNumberStr(generatorCardInfoUtil.redisClient.incr(REDIS_KEY , 1));
        sb.append(str);

        return sb.toString();
    }

    public static String getSecretKey(Integer money){
        StringBuffer sb = new StringBuffer();
        sb.append(SECRET_KEY_PREFIX);

        //金额
        String moneyStr = getNumberStr(Long.valueOf(String.valueOf(money)));
        sb.append(moneyStr);

        //随机码
        sb.append(randomNumber(12));

        return sb.toString();
    }

    /**
     * 随机生成数字字符串
     * @param length 字符串长度
     * @return
     */
    public static String randomNumber(int length){
        if(length <= 0){
            length = DEFAULT_LENGTH;
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
    private static String getNumberStr(Long number){
        if(number.intValue() > 9999L){
            number = 1L;
        }
        String numberStr = String.valueOf(number);
        String prefix = "";
        switch (numberStr.length()){
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
        return prefix + numberStr;
    }


}
