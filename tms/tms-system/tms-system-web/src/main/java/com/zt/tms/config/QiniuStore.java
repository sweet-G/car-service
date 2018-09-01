package com.zt.tms.config;

import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

/**
 * @author zhangtian
 * @date 2018/9/1
 */
@Component
public class QiniuStore {
    @Value("${qiniu.ak}")
    private String accessKey;
    @Value("${qiniu.sk}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;

    public String getUploadToken(){
        Auth auth = Auth.create(accessKey,secretKey);
        return auth.uploadToken(bucket);
    }
}
