package com.gazprom.InforamtionSystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequest {
    private Long userId;
    private String system;
    private Long validity;
    private DataRequest fillingDate;
    private DataRequest expiryDate;
}
