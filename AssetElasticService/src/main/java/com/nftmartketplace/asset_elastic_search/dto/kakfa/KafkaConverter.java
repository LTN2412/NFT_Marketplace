package com.nftmartketplace.asset_elastic_search.dto.kakfa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;

@Configuration
public class KafkaConverter {
    @Bean
    RecordMessageConverter converter() {
        return new JsonMessageConverter();
    }

    // @Bean
    // RecordMessageConverter converter() {
    // JsonMessageConverter converter = new JsonMessageConverter();
    // DefaultJackson2JavaTypeMapper typeMapper = new
    // DefaultJackson2JavaTypeMapper();
    // typeMapper.setTypePrecedence(TypePrecedence.TYPE_ID);
    // typeMapper.addTrustedPackages("com.common");
    // Map<String, Class<?>> mappings = new HashMap<>();
    // mappings.put("foo", Foo1.class);
    // mappings.put("bar", Bar1.class);
    // typeMapper.setIdClassMapping(mappings);
    // converter.setTypeMapper(typeMapper);
    // return converter;
    // }
}
