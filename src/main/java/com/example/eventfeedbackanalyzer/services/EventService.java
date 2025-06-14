package com.example.eventfeedbackanalyzer.services;

import com.example.eventfeedbackanalyzer.dtos.EventDto;
import com.example.eventfeedbackanalyzer.dtos.EventSummaryDto;
import com.example.eventfeedbackanalyzer.entities.Event;
import com.example.eventfeedbackanalyzer.mappers.EventMapper;
import com.example.eventfeedbackanalyzer.repositories.EventRepository;
import com.example.eventfeedbackanalyzer.repositories.FeedbackRepository;
import com.example.eventfeedbackanalyzer.repositories.SentimentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final FeedbackRepository feedbackRepository;
    private final SentimentRepository sentimentRepository;
    private final EventMapper eventMapper;

    public List<EventDto> findAll() {
        return eventMapper.toDtoList(eventRepository.findAll());
    }

    public EventDto createEvent(EventDto eventDto) {
        Event event = eventMapper.toEntity(eventDto);
        return eventMapper.toDto(eventRepository.save(event));
    }

    public EventSummaryDto getEventSummary(Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event == null) {
            return null;
        }
        EventSummaryDto eventSummary = new EventSummaryDto();
        eventSummary.setId(event.getId());
        eventSummary.setTitle(event.getTitle());
        eventSummary.setDescription(event.getDescription());
        Map<String, Long> eventRatings = new HashMap<>();
        eventRatings.put("Very Positive", 0L);
        eventRatings.put("Positive", 0L);
        eventRatings.put("Neutral", 0L);
        eventRatings.put("Negative", 0L);
        eventRatings.put("Very Negative", 0L);
        feedbackRepository.findAllByEventId(eventId).forEach(feedback -> {
            sentimentRepository.findById(feedback.getSentiment().getId()).ifPresent(sentiment -> {
                eventRatings.compute(sentiment.getLabel(),(k,v) -> v == null ? 1L : v + 1);
            });
        });
        eventSummary.setRatings(eventRatings);
        return eventSummary;
    }
}
