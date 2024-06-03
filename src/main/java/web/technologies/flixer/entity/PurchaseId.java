package web.technologies.flixer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseId implements Serializable {
    User user;
    Movie movie;
}
