package com.cx.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Administrator
 * @version V1.0
 * @desc AES 加密工具类
 */
public class AesUtil {
    //密码和OMS 保持一致
    public static final String PASSWORD_KEY = "3sdf;xc4h#cs3*51";
    private static final String KEY_ALGORITHM = "AES";
    /**
     *  默认的加密算法
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES 加密操作
     *
     * @param content  待加密内容
     * @param password 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String password) {
        try {// 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            byte[] byteContent = content.getBytes("utf-8");
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));
              // 加密
            byte[] result = cipher.doFinal(byteContent);
             // 通过Base64转码返回
            return Base64.encodeBase64String(result);
        } catch (Exception ex) {
            Logger.getLogger(AesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password) {
        try {
            // 实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            // 使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

            // 执行操作
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));

            return new String(result, "utf-8");
        } catch (Exception ex) {
            Logger.getLogger(AesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }


    /**
     * 生成加密秘钥
     *
     * @return
     */
    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {
        return new SecretKeySpec(password.getBytes(), KEY_ALGORITHM);// 转换为AES专用密钥
    }


    public static void main(String[] args) {
        List<String> ls = new ArrayList<String>(Arrays.asList("DJB21481-BK","ZHCUZD727-BK","MWC9442D-N","NKNJB-B--20","NKNJB-B--15","LBYK1780-198","MJBK537-4-WT","ZHCPA3875-BK","DB110/76-BK","HTC4340D-N","HTC4340D-N","LBYK320-8","XTKT608-BK"));
        System.out.println(JSON.toJSON(ls));
        Map map = new HashMap<String, String>(10);
        map.put("fabricCode", "MLMD179004-3");
        map.put("infos", JSON.toJSONString(ls));
        map.put("level", "A");
        map.put("pdCode", "DZ20");
        map.put("name", "测试用户");
        map.put("salesCode", "2019110910342547988");
        String s = "aEcYm9fwgMAUSt3vLyYqJFFM3CWYMCjXH5DWzE6HTGUFAmWioDMLbC5HeO5ixm6ljnzsq94XXlwz/8cj/CtM0y5ffw4ojzBsPZhuih35pAmlbTMOriCeaLmZJdt4ficBjiKQ8c1MKLVgAkgAvQ1uq6zvB9dC0mwyZzCCtWUSPkaOwyKBiKM20n2E2msbcz1sXLd5hEaO0/fNmXHrBEh4NPjtC+ASwfEXBx3EMySgSnmCmB66udd7Du6BS53RRcspjVVFqHzL4HDXay5jmApWP2gFgDSWO2Nd/4dVxvSRgjK19EuTnvnKXYeWdUjhjQ3NfvSk/iGQzGbqO0U6U8M0oyN5Wh9fNX4Bm+PZaL+qpl26byD3/6aBUFAdXl02w7XM";
        System.out.println(AesUtil.encrypt(JSON.toJSONString(map), PASSWORD_KEY));
    }

}