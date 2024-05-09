package web.technologies.flixer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TagLabel {
    THRILLER("THRILLER"),
    ACTION("ACTION"),
    CRIME("CRIME"),
    DRAMA("DRAMA"),
    ROMANCE("ROMANCE"),
    WAR("WAR"),
    ANIMATION("ANIMATION"),
    ADVENTURE("ADVENTURE"),
    MYSTERY("MYSTERY"),
    SCIENCE_FICTION("SCIENCE FICTION"),
    TV_MOVIE("TV MOVIE"),
    HORROR("HORROR"),
    HISTORY("HISTORY"),
    COMEDY("COMEDY"),
    DOCUMENTARY("DOCUMENTARY"),
    FANTASY("FANTASY"),
    WESTERN("WESTERN"),
    FAMILY("FAMILY"),
    MUSIC("MUSIC");

    private final String label;
}