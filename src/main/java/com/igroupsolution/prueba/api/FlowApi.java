package com.igroupsolution.prueba.api;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Getter
@Setter
public class FlowApi {

    private static Logger LOGGGER = LoggerFactory.getLogger(CustomerApi.class);
    @Value("${flow.api.endpoint}")
    private String flowApiEndpoint;
    @Value("${flow.api.key}")
    private String flowApiKey;
    @Value("${flow.api.secret.key}")
    private String flowApiSecretKey;

    public String getSignature(Map<String, String> params) {
        String sign = null;
        try {
            params.put("apiKey", flowApiKey);
            sign = params.entrySet()
                    .stream()
                    .map(e -> e.getKey() + e.getValue())
                    .collect(Collectors.joining(""));
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(flowApiSecretKey.getBytes("UTF-8"), "HmacSHA256");
            hmacSha256.init(secretKeySpec);
            return byteArrayToHex(hmacSha256.doFinal(sign.getBytes("UTF-8")));
        } catch (Exception ex) {
            LOGGGER.info("Error generando la firma");
        }
        return sign;
    }

    public String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String getApiEndpoint(String path, String query) {
        String endpoint = flowApiEndpoint + path;
        if (query != null) {
            endpoint += "?" + query;
        }
        return endpoint;
    }

    public String getApiQuery(String signature) {
        Map<String, String> params = new TreeMap<>();
        params.put("apiKey", flowApiKey);
        params.put("s", signature);
        return params.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));
    }

    public MultiValueMap<String, String> getData(Map<String, String> params) {
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        params.entrySet().forEach(param -> {
            data.add(param.getKey(), param.getValue());
        });
        return data;
    }
}
