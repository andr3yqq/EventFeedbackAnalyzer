package com.example.eventfeedbackanalyzer.mappers;

import com.example.eventfeedbackanalyzer.dtos.EventDto;
import com.example.eventfeedbackanalyzer.entities.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto toDto(Event event);
    List<EventDto> toDtoList(List<Event> events);
    Event toEntity(EventDto eventDto);
}

