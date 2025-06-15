package com.example.eventfeedbackanalyzer.services;

import com.example.eventfeedbackanalyzer.dtos.ApiResponseDto;
import com.example.eventfeedbackanalyzer.dtos.FeedbackDto;
import com.example.eventfeedbackanalyzer.entities.Feedback;
import com.example.eventfeedbackanalyzer.entities.Sentiment;
import com.example.eventfeedbackanalyzer.mappers.FeedbackMapper;
import com.example.eventfeedbackanalyzer.repositories.EventRepository;
import com.example.eventfeedbackanalyzer.repositories.FeedbackRepository;
import com.example.eventfeedbackanalyzer.repositories.SentimentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final SentimentRepository sentimentRepository;
    private final EventRepository eventRepository;
    private final FeedbackMapper feedbackMapper;
    @Autowired
    private final WebClient webClient;

    public FeedbackDto createFeedback(Long eventId, FeedbackDto feedbackDto) {
        Feedback feedback = feedbackMapper.toEntity(feedbackDto);
        feedback.setEvent(eventRepository.findById(eventId).orElse(null));
        feedback.setTimestamp(LocalDateTime.now());
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

    public Integer getFeedbackCountByEventId(Long eventId) {
        return feedbackRepository.findAllByEventId(eventId).size();
    }

    public Mono<Sentiment> analyzeSentiment(String feedback) {
        //String requestBody = "{\"inputs\":\"" + feedback + "\"}";
        Map<String, String> requestBody = Map.of("inputs", feedback);

        return webClient.post()
                .uri("/models/tabularisai/multilingual-sentiment-analysis")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<List<ApiResponseDto>>>() {})
                .map(responseDto -> {
                    Sentiment sentiment = new Sentiment();
                    sentiment.setLabel(responseDto.get(0).get(0).getLabel());
                    sentiment.setScore(responseDto.get(0).get(0).getScore());
                    return sentiment;
                });
    }

}
