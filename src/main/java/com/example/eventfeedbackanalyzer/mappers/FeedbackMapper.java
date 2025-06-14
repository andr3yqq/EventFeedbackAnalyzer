package com.example.eventfeedbackanalyzer.mappers;

import com.example.eventfeedbackanalyzer.dtos.FeedbackDto;
import com.example.eventfeedbackanalyzer.entities.Feedback;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackDto toDto(Feedback feedback);
    List<FeedbackDto> toDtoList(List<Feedback> feedbacks);
    Feedback toEntity(FeedbackDto feedbackDto);
}
