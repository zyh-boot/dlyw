package com.cx.common.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * *图片工具类
 *
 * @author Administrator
 * @date 2019/09/16
 */
@Slf4j
public class ImageUtil {

    private ImageUtil() {
    }

    /**
     * 描述：将图片地址进行base64编码
     */
    public static String encodeImgageToBase64(URL imageUrl) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageUrl);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            log.error("图片,base64编码:" + e1);
        } catch (IOException e) {
            log.error("图片,base64编码:" + e);
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        if (outputStream != null) {
            return encoder.encode(outputStream.toByteArray());
        }
        return null;
    }

    /**
     * 将图片文件进行base64编码
     */
    public static String encodeImgageToBase64(File imageFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            log.error("图片base64编码:" + e1);
        } catch (IOException e) {
            log.error("图片base64编码:" + e);
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        if (outputStream != null) {
            return encoder.encode(outputStream.toByteArray());
        }
        return null;
    }

    public static String decodeBase64ToImage(String base64, String path, String imgName) {
        BASE64Decoder decoder = new BASE64Decoder();
        File file = null;

        File filepat = new File(path);
        if (!filepat.exists()) {
            filepat.mkdirs(); // 新建文件路径
        }

        file = new File(path + imgName);
        try (FileOutputStream write = new FileOutputStream(file)) {

            String replase = base64.replaceFirst("data:(.*);base64,", "");
            byte[] decoderBytes = decoder.decodeBuffer(replase);
            write.write(decoderBytes);
        } catch (IOException e) {
            log.error("" + e);
        }
        return path + imgName;
    }

}