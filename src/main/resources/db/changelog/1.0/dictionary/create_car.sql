CREATE TABLE  car
(
    id         BIGINT PRIMARY KEY,
    model      VARCHAR(255) NOT NULL,
    horsepower INT,
    owner_id   BIGINT,
    FOREIGN KEY (owner_id) REFERENCES  person (id)
);