package ru.mts.media.platform.umc.dao.postgres.event;

import jakarta.persistence.*;
import lombok.Data;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "event")
public class EventPgEntity {

    @Id
    private String id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToMany
    @JoinTable(
            name = "event_venue",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "venue_reference_id", referencedColumnName = "referenceId")
    )
    private Set<VenuePgEntity> venues = new HashSet<>();

}
