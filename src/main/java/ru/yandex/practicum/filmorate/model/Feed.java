package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@Builder(toBuilder = true)
public class Feed {
    private Long eventId;
    @NotNull
    private Long userId;
    @NotNull
    private FeedOperation operation;
    @NotNull
    private Long entityId;
    @NotNull
    private FeedEventType eventType;
    private ZonedDateTime timestamp;

}
