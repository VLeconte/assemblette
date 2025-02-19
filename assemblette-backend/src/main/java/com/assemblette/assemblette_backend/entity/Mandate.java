package com.assemblette.assemblette_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mandates")
@Builder
public class Mandate {
    @Id
    private String id;

    @ManyToOne
    private Deputy deputy;

    @ManyToOne
    private Authority authority;

    @Column(nullable = false)
    private String startDate;

    private String endDate;
}
