package org.jeecg.modules.rider.fadada.config;

import com.fasc.open.api.config.HttpConfig;
import com.fasc.open.api.stratey.DefaultJsonStrategy;
import com.fasc.open.api.v5_1.client.OpenApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FadadaConfig {

    @Value("${fadada.appID}")
    private String appId;

    @Value("${fadada.appSecret}")
    private String appSecret;

    @Value("${fadada.serverUrl}")
    private String serverUrl;

    @Bean
    public OpenApiClient openApiClient() {
        OpenApiClient client = new OpenApiClient(appId, appSecret, serverUrl);
        // 配置HTTP超时
        HttpConfig httpConfig = new HttpConfig();
        httpConfig.setConnectTimeout(100000);
        httpConfig.setReadTimeout(100000);
        client.setHttpConfig(httpConfig);
        // 使用默认JSON序列化策略
        client.setJsonStrategy(new DefaultJsonStrategy());
        return client;
    }
}
