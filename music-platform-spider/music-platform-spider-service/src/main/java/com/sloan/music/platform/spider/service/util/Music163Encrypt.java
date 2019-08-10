package com.sloan.music.platform.spider.service.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author kakaluote zhaozhong@youzan.com
 * @date 2019/8/11
 **/
@Slf4j
public final class Music163Encrypt {


    /***
     * 密钥
     */
    private static final String S_KEY = "0CoJUm6Qyw8W8jud";

    /**
     * 偏移量
     */
    private static final String IV_PARAMETER = "0102030405060708";

    private static final String SEC_KEY = "257348aecb5e556c066de214e531faadd1c55d814f9be95fd06d6bff9f4c7a41f831f6394d5a3fd2e3881736d94a02ca919d952872e7d0a50ebfa1769a7a62d512f5f1ca21aec60bc3819a9c3ffca5eca9a0dba6d6f7249b06f5965ecfff3695b54e1c28f3f624750ed39e7de08fc8493242e26dbc4484a01c76f739e135637c";

    public static String getSecKey() {

        return SEC_KEY;
    }

    public static String getParams(String songId, Integer page, Integer pageSize) {

        String content = makeContent(songId, page, pageSize);
        return Music163Encrypt.AESEncrypt((Music163Encrypt.AESEncrypt(content, S_KEY)), "FFFFFFFFFFFFFFFF");
    }

    /**
     * aes加密
     * @param content 加密内容
     * @param sKey 偏移量
     * @return
     */
    @SneakyThrows
    private static String AESEncrypt(String content,String sKey) {
        byte[] encryptedBytes;
        byte[] byteContent = content.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(sKey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
        encryptedBytes = cipher.doFinal(byteContent);
        return new String(Base64Utils.encode(encryptedBytes), "UTF-8");
    }

    /**
     * @param songId
     * @param page
     * @param pageSize
     * @return
     */
    private static String makeContent(String songId, Integer page, Integer pageSize) {

        int offset = (page - 1) * 20;
        PageContent pageContent = new PageContent();
        pageContent.setRid("R_SO_4_%s");
        pageContent.setOffset(String.valueOf(offset));
        pageContent.setTotal("true");
        pageContent.setLimit(String.valueOf(pageSize));
        pageContent.setCsrf_token("");

        return String.format(JSON.toJSONString(pageContent), songId);
    }

    @Data
    @Accessors(chain = true)
    private static class PageContent {

        private String rid;

        private String offset;

        private String total;

        private String limit;

        private String csrf_token;
    }
}
