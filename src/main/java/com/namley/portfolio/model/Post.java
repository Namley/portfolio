package com.namley.portfolio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username = "Namley";
    private String content;
    private Instant createdAt;
    private String imagePath;

    public String getCreatedAtTimeZone() {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(createdAt, ZonedDateTime.now().getZone());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a · d. MMM. yyyy", java.util.Locale.ENGLISH).withZone(ZonedDateTime.now().getZone());

        return formatter.format(zonedDateTime);
    }
}