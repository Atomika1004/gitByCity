package com.atomika.gitByCity.multiKey;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class LikeRatingKey implements Serializable {
    private Long clientId;
    private Long routeId;
}
