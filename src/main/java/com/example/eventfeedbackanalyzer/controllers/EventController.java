package com.example.eventfeedbackanalyzer.controllers;

import com.example.eventfeedbackanalyzer.dtos.EventDto;
import com.example.eventfeedbackanalyzer.dtos.EventSummaryDto;
import com.example.eventfeedbackanalyzer.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        return ResponseEntity.ok(eventService.createEvent(eventDto));
    }

    @GetMapping("/{eventId}/summary")
    public ResponseEntity<EventSummaryDto> getEventSummary(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventSummary(eventId));
    }
}
