package com.example.eventfeedbackanalyzer.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SentimentDto {
    private Long id;
    private String label;
    private Double score;
}
