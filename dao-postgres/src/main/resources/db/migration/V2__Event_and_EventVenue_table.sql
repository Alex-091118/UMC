CREATE TABLE IF NOT EXISTS event
(
    id           TEXT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    start_time   TIMESTAMP NOT NULL,
    end_time     TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS event_venue
(
    event_id            TEXT NOT NULL,
    venue_reference_id  VARCHAR(255) NOT NULL,
    PRIMARY KEY (event_id, venue_reference_id),
    FOREIGN KEY (event_id) REFERENCES event(id),
    FOREIGN KEY (venue_reference_id) REFERENCES venue(reference_id)
);