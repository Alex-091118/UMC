package ru.mts.media.platform.umc.dao.postgres.event;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventPgRepository extends JpaRepository<EventPgEntity, String> {

    @EntityGraph(attributePaths = {"venues"})
    List<EventPgEntity> findAll();

}
