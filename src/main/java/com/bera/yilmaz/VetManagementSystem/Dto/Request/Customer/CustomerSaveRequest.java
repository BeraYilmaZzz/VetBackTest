    package com.bera.yilmaz.VetManagementSystem.Dto.Request.Customer;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class CustomerSaveRequest {
        private String name;
        private String phone;
        private String mail;
        private String address;
        private String city;
    }
