package com.example.eventfeedbackanalyzer.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDto {
    private Long id;
    private String title;
    private String description;
}
