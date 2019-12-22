package ru.priamosudov.hotelme.mapping;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMappingConfiguration {

    @Bean
    @Primary
    public ModelMapper commonModelMapper() {
        return new ModelMapper();
    }
}
