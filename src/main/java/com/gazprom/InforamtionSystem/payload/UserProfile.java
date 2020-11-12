package com.gazprom.InforamtionSystem.payload;

import com.gazprom.InforamtionSystem.model.Department;
import com.gazprom.InforamtionSystem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    private Long id;
    private String userName;
    private String name;
    private String lastName;
    private String middleName;
    private Role role;
    private Department department;
}
