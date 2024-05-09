package web.technologies.flixer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieTagId implements Serializable {
    private Long movie;
    private Long tag;
}