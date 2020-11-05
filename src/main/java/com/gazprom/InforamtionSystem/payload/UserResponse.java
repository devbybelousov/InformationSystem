package com.gazprom.InforamtionSystem.payload;

import com.gazprom.InforamtionSystem.model.Department;
import com.gazprom.InforamtionSystem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userName;
    private String password;
    private String name;
    private String lastName;
    private String middleName;
    private Role role;
    private Department department;
}
