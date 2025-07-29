CREATE EXTENSION IF NOT EXISTS postgis;

---Module
CREATE TABLE MODULE(
    module_id SERIAL PRIMARY KEY,
    module_location GEOGRAPHY(POINT, 4326) NOT NULL,
    module_location_name varchar(255),
    module_n int NOT NULL
);

---Status   -- AVAILABLE, UNAVAILABLE, MAINTENANCE
CREATE TABLE MODULE_STATUS(
    module int PRIMARY KEY REFERENCES MODULE(module_id) ON DELETE CASCADE,
    module_status varchar(50)
);

SELECT * FROM information_schema.tables WHERE table_name ILIKE 'module';