package ru.magicvolley.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "programs")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramEntity {

    @Id
    private UUID id;
    private String info;
    private String dayOfWeek;
    @Column(name = "order_number")
    private Integer order;

    @CreationTimestamp
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Column(name = "camp_id")
    private UUID campId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camp_id", insertable = false, updatable = false)
    private CampEntity camp;

    @Version
    private Long version;
}
