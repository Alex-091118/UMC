package ru.mts.media.platform.umc.dao.postgres.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPgRepository extends JpaRepository<EventPgEntity, String> {
}
