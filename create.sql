CREATE TABLE vehicle (
    id SERIAL PRIMARY KEY,
    registration_number VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(255) NOT NULL,
    capacity INTEGER NOT NULL
);

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    passenger_name VARCHAR(255) NOT NULL,
    issued_at TIMESTAMP NOT NULL,
    validated BOOLEAN DEFAULT FALSE,
    validated_at TIMESTAMP,
    vehicle_id INTEGER NOT NULL,
    CONSTRAINT fk_ticket_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicle(id)
);

INSERT INTO vehicle (registration_number, type, capacity)
VALUES ('UN1234', 'bus', 50);

INSERT INTO ticket (code, passenger_name, issued_at, validated, validated_at, vehicle_id)
VALUES ('TCKT-0001', 'John Doe', NOW(), false, NULL, 1);
