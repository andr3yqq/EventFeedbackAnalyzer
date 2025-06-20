package com.example.eventfeedbackanalyzer.dtos;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FeedbackDto {
    private Long id;
    private String username;
    private String feedback;
    private LocalDateTime timestamp;
    private Long eventId;
    private Long sentimentId;
}
