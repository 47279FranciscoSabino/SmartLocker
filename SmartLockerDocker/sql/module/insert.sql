INSERT INTO MODULE( module_location, module_location_name, module_n)
VALUES (ST_MakePoint(-9.116494150002076, 38.75691186259142), 'Isel',10),
       (ST_MakePoint(-9.097519, 38.767714), 'Oriente', 6),
       (ST_MakePoint(-9.021307, 38.530180), 'Azeitão', 10),
       (ST_MakePoint(-9.166618, 38.740364), 'Sete-Rios', 16);

---Status
INSERT INTO MODULE_STATUS(module,  module_status )
VALUES (1,  'AVAILABLE'),
       (2, 'UNAVAILABLE'),
       (3, 'MAINTENANCE'),
       (4, 'MAINTENANCE');