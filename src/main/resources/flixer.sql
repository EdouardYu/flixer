CREATE TABLE role (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    label VARCHAR NOT NULL
);

INSERT INTO role(label) VALUES ('ADMINISTRATOR'), ('USER'), ('SUPPLIER');

CREATE TABLE "user" (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username VARCHAR UNIQUE NOT NULL,
    email VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    birthday DATE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    role_id INTEGER NOT NULL,
    CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE validation (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    created_at TIMESTAMP NOT NULL,
    expired_at TIMESTAMP NOT NULL,
    activated_at TIMESTAMP,
    activation_code CHARACTER(6) NOT NULL,
    user_id INTEGER NOT NULL,
    CONSTRAINT validation_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE jwt (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    value VARCHAR NOT NULL,
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    deactivated BOOLEAN NOT NULL DEFAULT FALSE,
    user_id INTEGER NOT NULL,
    CONSTRAINT jwt_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE tag (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    label VARCHAR NOT NULL
);

INSERT INTO tag(label) VALUES
    ('THRILLER'),
    ('ACTION'),
    ('CRIME'),
    ('DRAMA'),
    ('ROMANCE'),
    ('WAR'),
    ('ANIMATION'),
    ('ADVENTURE'),
    ('MYSTERY'),
    ('SCIENCE FICTION'),
    ('TV MOVIE'),
    ('HORROR'),
    ('HISTORY'),
    ('COMEDY'),
    ('DOCUMENTARY'),
    ('FANTASY'),
    ('WESTERN'),
    ('FAMILY'),
    ('MUSIC');

CREATE TABLE movie (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title VARCHAR NOT NULL,
    url VARCHAR NOT NULL,
    description TEXT NOT NULL,
    released_at TIMESTAMP NOT NULL,
    poster_url VARCHAR NOT NULL,
    director VARCHAR NOT NULL,
    supplier_id INTEGER NOT NULL,
    price DECIMAL(3, 2) NOT NULL,
    CONSTRAINT supplier_fk FOREIGN KEY (supplier_id) REFERENCES "user"(id)
);

CREATE TABLE movie_tag (
    movie_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    PRIMARY KEY (movie_id, tag_id),
    CONSTRAINT movie_fk FOREIGN KEY (movie_id) REFERENCES movie(id),
    CONSTRAINT tag_fk FOREIGN KEY (tag_id) REFERENCES tag(id)
);

CREATE TABLE rating (
    movie_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    value DECIMAL(1,2) NOT NULL,
    PRIMARY KEY (movie_id, user_id),
    CONSTRAINT rating_movie_fk FOREIGN KEY (movie_id) REFERENCES movie(id),
    CONSTRAINT rating_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE history (
    movie_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    watched_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (movie_id, user_id),
    CONSTRAINT history_movie_fk FOREIGN KEY (movie_id) REFERENCES movie(id),
    CONSTRAINT history_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE TABLE subscription_plan (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    duration INTERVAL NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE subscription_status (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    label VARCHAR NOT NULL
);

INSERT INTO subscription_status(label) VALUES ('STARTED'), ('RENEWED'), ('CANCELLED'), ('EXPIRED');

CREATE TABLE subscription (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id INTEGER NOT NULL,
    plan_id INTEGER NOT NULL,
    status_id INTEGER NOT NULL,
    subscribed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    renewed_at TIMESTAMP,
    started_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP NOT NULL,
    CONSTRAINT subscription_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id),
    CONSTRAINT plan_fk FOREIGN KEY (plan_id) REFERENCES subscription_plan(id),
    CONSTRAINT status_fk FOREIGN KEY (status_id) REFERENCES subscription_status(id)
);

CREATE TABLE purchase (
    user_id INTEGER NOT NULL,
    movie_id INTEGER NOT NULL,
    purchased_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, movie_id),
    CONSTRAINT purchase_user_fk FOREIGN KEY (user_id) REFERENCES "user"(id),
    CONSTRAINT purchase_movie_fk FOREIGN KEY (movie_id) REFERENCES movie(id)
);
INSERT INTO public.user (username, email, password, age, amount, enabled, role_id)
VALUES ('Youtube', 'youtube@gmail.com', 123, -1, 0, TRUE, 3)
ON CONFLICT (username) DO NOTHING;

/*Before using this check if user_id = 2 exists*/
INSERT INTO rating (movie_id, user_id, value)
VALUES
    (28, 2, 4.0),
    (29, 2, 3.5),
    (30, 2, 4.0),
    (31, 2, 4.0),
    (32, 2, 3.5),
    (33, 2, 4.0),
    (34, 2, 3.0),
    (35, 2, 4.0),
    (36, 2, 4.0),
    (37, 2, 3.0),
    (38, 2, 4.0),
    (39, 2, 3.5),
    (40, 2, 3.0),
    (41, 2, 4.0),
    (42, 2, 4.0),
    (43, 2, 4.0)
ON CONFLICT (movie_id, user_id) DO NOTHING;

/*Before using this : check if user_id = 10 exist*/
/*INSERT INTO history (movie_id, user_id, watched_at)
VALUES
    (30, 10, '2023-05-07 15:30:00'),
    (40, 10, '2023-04-15 18:45:00'),
    (50, 10, '2023-03-20 20:15:00'),
    (60, 10, '2023-02-10 12:00:00'),
    (70, 10, '2023-01-05 08:30:00');*/