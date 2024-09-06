package com.nftmarketplace.asset_service.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

    @Bean
    Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dusingol9");
        config.put("api_key", "618291643492735");
        config.put("api_secret", "3VNR388ywqVrvNw5jWn-pmcdgGQ");
        return new Cloudinary(config);
    }
}
