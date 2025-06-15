package com.example.eventfeedbackanalyzer.mappers;

import com.example.eventfeedbackanalyzer.dtos.FeedbackDto;
import com.example.eventfeedbackanalyzer.entities.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "sentimentId", source = "sentiment.id")
    FeedbackDto toDto(Feedback feedback);
    List<FeedbackDto> toDtoList(List<Feedback> feedbacks);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "sentiment", ignore = true)
    Feedback toEntity(FeedbackDto feedbackDto);
}
