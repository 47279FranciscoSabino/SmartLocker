CREATE TABLE HASH(
    hash varchar(255) NOT NULL UNIQUE,
    locker integer,
    hash_qr BYTEA NOT NULL
);
