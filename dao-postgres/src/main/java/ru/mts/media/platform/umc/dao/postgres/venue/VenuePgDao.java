package ru.mts.media.platform.umc.dao.postgres.venue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgMapper;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSave;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class VenuePgDao implements VenueSot {
    private final VenuePgRepository repository;
    private final VenuePgMapper venuePgMapper;
    private final EventPgMapper eventPgMapper;

    public Optional<Venue> getVenueByReferenceId(String id) {
        return Optional.of(id)
                .map(repository::findByReferenceId)
                .map(venuePgMapper::asModel);
    }

    @Override
    public Optional<Venue> getVenueById(FullExternalId externalId) {
        return Optional.of(externalId)
                .map(venuePgMapper::asPk)
                .flatMap(repository::findById)
                .map(venuePgMapper::asModel);
    }

    @Override
    public List<Venue> getVenuesWithEvents() {
        return repository.findAll().stream()
                .map(venuePgEntity -> {
                    var venue = venuePgMapper.asModel(venuePgEntity);
                    venue.setEvents(venuePgEntity.getEvents().stream().map(eventPgMapper::asModel).toList());
                    return venue;
                })
                .toList();
    }

    @EventListener
    public void handleVenueCreatedEvent(VenueSave evt) {
        evt.unwrap()
                .map(venuePgMapper::asEntity)
                .ifPresent(repository::save);
    }
}
