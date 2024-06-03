package web.technologies.flixer.dto;

import lombok.Data;

@Data
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
