package com.gazprom.InforamtionSystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private Long id;
    private UserProfile user;
    private String system;
    private DataRequest expiryDate;
    private String status;
    private DataRequest fillingDate;
}
