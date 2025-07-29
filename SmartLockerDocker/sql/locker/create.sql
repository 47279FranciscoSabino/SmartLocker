---LOCKER
CREATE TABLE LOCKER (
    locker_id SERIAL PRIMARY KEY,
    locker_module int NOT NULL,
    locker_hash varchar(255) NOT NULL UNIQUE,
    locker_ip varchar(255) NOT NULL UNIQUE,
    locker_active boolean NOT NULL,
    FOREIGN KEY (locker_module) REFERENCES MODULE(module_id) ON DELETE CASCADE
);

---Status   -- IN_USE, AVAILABLE, MAINTENANCE
CREATE TABLE LOCKER_STATUS(
    locker int,
    locker_status varchar(50),
    PRIMARY KEY (locker),
    FOREIGN KEY (locker) REFERENCES LOCKER (locker_id) ON DELETE CASCADE
);