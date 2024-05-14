package web.technologies.flixer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import web.technologies.flixer.entity.Movie;

import java.time.Instant;

@Data
@AllArgsConstructor
public class HistoryUserDTO {
    private Movie movie;
    private Instant watched_at;
}