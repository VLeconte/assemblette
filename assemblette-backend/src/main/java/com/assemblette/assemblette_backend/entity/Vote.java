package com.assemblette.assemblette_backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "votes")
@Builder
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Convert this to Unique ID format
    private Long id;

    @ManyToOne
    private Ballot ballot;

    @ManyToOne
    private Deputy deputy;

    @Column(nullable = false)
    private String state;
}
