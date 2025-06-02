CREATE EXTENSION IF NOT EXISTS postgis;

---Module
CREATE TABLE MODULE(
    module_id SERIAL PRIMARY KEY,
    module_location GEOGRAPHY(POINT, 4326),
    module_n int
);

---Status
CREATE TABLE MODULE_STATUS(
    module int PRIMARY KEY REFERENCES MODULE(module_id),
    module_status varchar(50),
    module_location_name varchar(255)
);

SELECT * FROM information_schema.tables WHERE table_name ILIKE 'module';