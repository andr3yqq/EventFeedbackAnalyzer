package com.example.eventfeedbackanalyzer.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventSummaryDto {
    private Long id;
    private String title;
    private String description;
    private Map<String, Long> ratings;
}
