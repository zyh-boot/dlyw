package com.cx.common.utils;


import com.alibaba.fastjson.JSON;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;

/**
 * AES加解密
 * <p>
 * Created by yyh on 2015/10/9.
 */
public class AESUtils {

    public static final String PASSWORD_KEY = "3sdf;xc4h#cs3*51";
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";

    /**
     * SecretKeySpec类是KeySpec接口的实现类,用于构建秘密密钥规范
     */
    private SecretKeySpec key;

    public AESUtils() {
        key = new SecretKeySpec(PASSWORD_KEY.getBytes(), ALGORITHM);
    }

    /**
     * hex字符串 转 byte数组
     *
     * @param s
     * @return
     */
    private static byte[] hex2byte(String s) {
        if (s.length() % 2 == 0) {
            return hex2byte(s.getBytes(), 0, s.length() >> 1);
        } else {
            return hex2byte("0" + s);
        }
    }

    private static byte[] hex2byte(byte[] b, int offset, int len) {
        byte[] d = new byte[len];
        for (int i = 0; i < len * 2; i++) {
            int shift = i % 2 == 1 ? 0 : 4;
            d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
        }
        return d;
    }

    public static void main(String[] args) throws Exception {
        AESUtils util = new AESUtils(); // 密钥
        String s="aEcYm9fwgMAUSt3vLyYqJFFM3CWYMCjXH5DWzE6HTGXtP7V69GqBVPegOK4ZKW7dzcxptwkDipuz18nX1xNBKnH6+/vyiuX/mVss/EMUSgOLC/bCApFaIoiy3Xps8YaD9qM8tWueilcvZHwHpWA1oprg6dtLFEhcoap2LOXRnt3Dhq43ENceJSRKGWLgB+jRhG5XTBH4r6s/hg9itfN8Eh2JiatN87NoijEzpJrdOr+94rzImZzE4DXwadSbnkhh/PsRCS1FO8Y8TlJjPwlRD2PpHtyBNDdNJdVQVNhWDag=";
        //String s = "aEcYm9fwgMAUSt3vLyYqJFFM3CWYMCjXH5DWzE6HTGXTIiXfLBcmQWwqZYon8v4xPMzFGF5Gjjf+R5zfxvPV1ulXZKAoSAV+VehH8MSI2lS+Y5jdwzNTV7R4BmQkhPaj237pkhIjt0BMVeybfHRMVnnwXPmKsjLIkffnDNQzRRifyoRDYqNrKuOKyA1WC2HgwhoVUWfM/UBY/WNjiA/DhTh6a9c4vV0URrDR0NUpATcyor1rOq/MC/+NaJBLvbq0B9xdgX+yiW2TYK4r1+ZUnY0RAlu2Ejmh5l7jvzhVBc168MwRRqlCu9SwROe6ZKTAvfPHYKN00JvyCNLofAyzj7Nf7atNch0VEyEb5Qigt+QLoKLSYdRngR0dIasSxNpVYRNsH79b3LMYU6mCWczCFaHzIUG3aUS6lL/qDhJ+WVc143HePKCqx23I8fQnuNon";
        System.out.println("cardNo:" + util.encryptData("1234")); // 加密
        Map<String, String> sMap = JSON.parseObject(util.decryptData(s), Map.class);
        System.out.println("exp:" + sMap.get("infos"));
        System.out.println("exp:" + util.decryptData(s)); // 解密
    }

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String encryptData(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR); // 创建密码器
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
        return new BASE64Encoder().encode(cipher.doFinal(data.getBytes()));
    }

    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public String decryptData(String base64Data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(base64Data)));
    }
}
