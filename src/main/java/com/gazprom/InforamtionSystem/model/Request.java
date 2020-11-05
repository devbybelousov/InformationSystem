package com.gazprom.InforamtionSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "request")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "filing_date")
    private Timestamp filingDate;

    @Column(name = "validity")
    private Long validity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User department;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "system_id")
    public InformationSystem informationSystem;
}
