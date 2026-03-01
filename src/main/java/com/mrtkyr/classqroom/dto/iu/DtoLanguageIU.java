package com.mrtkyr.classqroom.dto.iu;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoLanguageIU {

    @NotNull(message = "Language Name cannot be null!")
    private String languageName;
}
