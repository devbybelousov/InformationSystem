package com.gazprom.InforamtionSystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FillingData {
    private int day;
    private int month;
    private int year;
}
