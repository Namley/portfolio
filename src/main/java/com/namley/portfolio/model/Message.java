package com.namley.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@RequiredArgsConstructor
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageContent;
    private String username;
    private String profileImageUrl;
    private Instant createdAt;

    public String getCreatedAtTimeZone() {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(createdAt, ZonedDateTime.now().getZone());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a · d. MMM. yyyy", java.util.Locale.ENGLISH).withZone(ZonedDateTime.now().getZone());

        return formatter.format(zonedDateTime);
    }

}
