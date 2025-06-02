INSERT INTO MODULE( module_location, module_n)
VALUES (ST_MakePoint(-9.116494150002076, 38.75691186259142), 10),
       (ST_MakePoint(-9.097519, 38.767714), 6),
       (ST_MakePoint(-9.021307, 38.530180), 10),
       (ST_MakePoint(-9.166618, 38.740364), 16);

---Status
INSERT INTO MODULE_STATUS(module, module_location_name, module_status )
VALUES (1, 'Isel', 'AVAILABLE'),
       (2, 'Oriente','UNAVAILABLE'),
       (3, 'Azeitão', 'MAINTENANCE'),
       (4, 'Sete-Rios', 'MAINTENANCE');