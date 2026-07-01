package com.bera.yilmaz.VetManagementSystem.Dto.Request.Animal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDeleteRequest {
    @NotNull
    private long id;
}
