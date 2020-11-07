package com.gazprom.InforamtionSystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CipherRequest {
    private String cipher;
    private String publicKey;
}
