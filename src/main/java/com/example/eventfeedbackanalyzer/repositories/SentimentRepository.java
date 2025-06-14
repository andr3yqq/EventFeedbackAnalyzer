package com.example.eventfeedbackanalyzer.repositories;

import com.example.eventfeedbackanalyzer.entities.Sentiment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentimentRepository extends JpaRepository<Sentiment, Long> {
}
