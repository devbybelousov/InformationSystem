package com.gazprom.InforamtionSystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequest {
    private Long userId;
    private String system;
    private Long validity;
    private FillingData date;
}
