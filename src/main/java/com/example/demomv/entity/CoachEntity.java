package com.example.demomv.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "coach")
@EqualsAndHashCode(of = "id")
public class CoachEntity {

    @Id
    private UUID id;
    private String name;
    private String surename;
    private String info;

//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
//    private CampEntity camp;
}
