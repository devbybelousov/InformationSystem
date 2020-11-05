package com.gazprom.InforamtionSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    public Department department;

    public User(@Length(min = 5, message = "*Your user name must have at least 5 characters") @NotEmpty(message = "*Please provide a user name") String userName, @Length(min = 5, message = "*Your password must have at least 5 characters") @NotEmpty(message = "*Please provide your password") String password, @NotEmpty(message = "*Please provide your name") String name, @NotEmpty(message = "*Please provide your last name") String lastName, @NotEmpty(message = "*Please provide your middle name") String middleName, Set<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.middleName = middleName;
        this.roles = roles;
    }

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
