package ru.mts.media.platform.umc.dao.postgres.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.domain.event.EventSave;
import ru.mts.media.platform.umc.domain.event.EventSot;
import ru.mts.media.platform.umc.domain.gql.types.Event;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventPgDao implements EventSot {

    private final EventPgRepository eventPgRepository;
    private final EventPgMapper mapper;

    @Override
    public List<Event> getEvents() {
        return eventPgRepository.findAll().stream().map(mapper::asModel).toList();
    }

    @EventListener
    public void handleVenueCreatedEvent(EventSave evt) {
        evt.unwrap()
                .map(mapper::asEntity)
                .ifPresent(eventPgRepository::save);
    }
}
