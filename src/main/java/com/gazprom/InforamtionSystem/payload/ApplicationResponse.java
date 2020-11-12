package com.gazprom.InforamtionSystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private Long id;
    private UserProfile user;
    private String system;
    private Long validity;
    private String status;
    private Timestamp date;
}
