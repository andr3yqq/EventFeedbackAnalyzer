package com.example.eventfeedbackanalyzer.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String feedback;
    private LocalDateTime timestamp;
    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;
    @OneToOne
    @JoinColumn(name = "sentimentId")
    private Sentiment sentiment;
}
