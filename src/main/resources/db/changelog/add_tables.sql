--liquibase formatted sql

--changeset valery:1
CREATE TABLE tournaments (
                             id UUID PRIMARY KEY,
                             type VARCHAR(20),
                             status VARCHAR(20),
                             date DATE,
                             stage INT
);

CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(100),
                       password VARCHAR(255),
                       firstname VARCHAR(50),
                       surname VARCHAR(50),
                       rating INTEGER,
                       role VARCHAR(20)
);

CREATE TABLE extensions (
                            id UUID PRIMARY KEY,
                            tournament_id UUID REFERENCES tournaments(id),
                            name VARCHAR(70),
                            value VARCHAR(50)
);

CREATE TABLE tournament_participants (
                                         id UUID PRIMARY KEY,
                                         user_id UUID REFERENCES users(id),
                                         tournament_id UUID REFERENCES tournaments(id)
);

CREATE TABLE matches (
                         id UUID PRIMARY KEY,
                         tournament_id UUID REFERENCES tournaments(id),
                         user1 UUID REFERENCES users(id),
                         user2 UUID REFERENCES users(id),
                         next_match UUID REFERENCES matches(id)
);

CREATE TABLE rounds (
                        id UUID PRIMARY KEY,
                        round_number INTEGER,
                        score1 INTEGER,
                        score2 INTEGER,
                        match_id UUID REFERENCES matches(id)
);
