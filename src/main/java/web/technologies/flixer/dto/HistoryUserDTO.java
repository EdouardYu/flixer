package web.technologies.flixer.dto;

import web.technologies.flixer.entity.Movie;

import java.sql.Timestamp;

public record HistoryUserDTO(Movie movie, Timestamp watched_at) {}
