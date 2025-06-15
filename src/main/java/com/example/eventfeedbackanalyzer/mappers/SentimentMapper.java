package com.example.eventfeedbackanalyzer.mappers;

import com.example.eventfeedbackanalyzer.dtos.SentimentDto;
import com.example.eventfeedbackanalyzer.entities.Sentiment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SentimentMapper {
    SentimentDto toDto(Sentiment sentiment);
    List<SentimentDto> toDtoList(List<Sentiment> sentiments);
    @Mapping(target = "id", ignore = true)
    Sentiment toEntity(SentimentDto sentimentDto);
}
