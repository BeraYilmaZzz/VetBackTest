package com.bera.yilmaz.VetManagementSystem.Dto.Request.Customer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDeleteRequest {
    @NotNull
    private long id;
}
