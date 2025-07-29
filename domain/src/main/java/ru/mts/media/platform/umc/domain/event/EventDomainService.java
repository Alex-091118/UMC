package ru.mts.media.platform.umc.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.mts.media.platform.umc.domain.gql.types.SaveEventInput;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventDomainService {

    private final ApplicationEventPublisher eventPublisher;
    private final VenueSot venueSot;
    private final EventDomainServiceMapper mapper;

    public EventSave save(String venueReferenceId, SaveEventInput input) {
        return venueSot.getVenueByReferenceId(venueReferenceId)
                .map(venue -> {
                    var event = mapper.toEvent(input);
                    event.setId(UUID.randomUUID().toString());
                    event.setVenues(List.of(venue));
                    var eventSave = new EventSave(event);
                    eventPublisher.publishEvent(eventSave);
                    return eventSave;
                }).orElseThrow(() -> new NoSuchElementException("Venue with reference_id = %s not exists".formatted(venueReferenceId)));
    }

}
