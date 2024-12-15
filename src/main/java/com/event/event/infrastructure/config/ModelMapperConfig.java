package com.event.event.infrastructure.config;

import com.event.event.entity.Event;
import com.event.event.infrastructure.events.dto.EventOrganizer.GetEventResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        modelMapper.typeMap(Event.class, GetEventResponseDTO.class)
                .addMapping(src -> src.getOrganizer().getId(), GetEventResponseDTO::setOrganizerId);
        return modelMapper;
    }
}
