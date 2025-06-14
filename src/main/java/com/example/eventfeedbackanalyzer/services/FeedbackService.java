package com.example.eventfeedbackanalyzer.services;

import com.example.eventfeedbackanalyzer.dtos.ApiResponseDto;
import com.example.eventfeedbackanalyzer.dtos.FeedbackDto;
import com.example.eventfeedbackanalyzer.entities.Feedback;
import com.example.eventfeedbackanalyzer.entities.Sentiment;
import com.example.eventfeedbackanalyzer.mappers.FeedbackMapper;
import com.example.eventfeedbackanalyzer.repositories.FeedbackRepository;
import com.example.eventfeedbackanalyzer.repositories.SentimentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final SentimentRepository sentimentRepository;
    private final FeedbackMapper feedbackMapper;
    private final WebClient webClient;

    public FeedbackDto createFeedback(Long eventId, FeedbackDto feedbackDto) {
        feedbackDto.setEventId(eventId);
        Feedback feedback = feedbackMapper.toEntity(feedbackDto);
        Sentiment sentiment = analyzeSentiment(feedback.getFeedback()).block();
        if (sentiment != null) {
            sentimentRepository.save(sentiment);
            feedback.setSentiment(sentiment);
        }
        return feedbackMapper.toDto(feedbackRepository.save(feedback));
    }

    public List<FeedbackDto> getAllFeedbackByEventId(Long eventId) {
        return feedbackMapper.toDtoList(feedbackRepository.findAllByEventId(eventId));
    }

    public Mono<Sentiment> analyzeSentiment(String feedback) {
        //String requestBody = "{\"inputs\":\"" + feedback + "\"}";
        Map<String, String> requestBody = Map.of("inputs", feedback);

        return webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(ApiResponseDto.class)
                .next()
                .map(responseDto -> {
                    Sentiment sentiment = new Sentiment();
                    sentiment.setLabel(responseDto.getLabel());
                    sentiment.setScore(responseDto.getScore());
                    return sentiment;
                });
    }

}
