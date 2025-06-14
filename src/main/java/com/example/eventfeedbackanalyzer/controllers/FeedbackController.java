package com.example.eventfeedbackanalyzer.controllers;

import com.example.eventfeedbackanalyzer.dtos.FeedbackDto;
import com.example.eventfeedbackanalyzer.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("/{eventId}/feedback")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksByEventId(@PathVariable Long eventId) {
        return ResponseEntity.ok(feedbackService.getAllFeedbackByEventId(eventId));
    }

    @PostMapping("/{eventId}/feedback")
    public ResponseEntity<FeedbackDto> createFeedback(@PathVariable Long eventId, @RequestBody FeedbackDto feedback) {
        FeedbackDto createdFeedback = feedbackService.createFeedback(eventId, feedback);
        return ResponseEntity.created(URI.create("/" + eventId + "/feedback/" + createdFeedback.getId())).body(createdFeedback);
    }
}
