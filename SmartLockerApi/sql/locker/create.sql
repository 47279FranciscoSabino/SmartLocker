---LOCKER
CREATE TABLE LOCKER (
    locker_id SERIAL PRIMARY KEY,
    locker_module int NOT NULL,
    locker_qr varchar(255) UNIQUE ,
    locker_active boolean NOT NULL,
    FOREIGN KEY (locker_module) REFERENCES MODULE(module_id)
);

---Status
CREATE TABLE LOCKER_STATUS(
    locker int,
    locker_status varchar(50),
    PRIMARY KEY (locker),
    FOREIGN KEY (locker) REFERENCES LOCKER (locker_id)

)