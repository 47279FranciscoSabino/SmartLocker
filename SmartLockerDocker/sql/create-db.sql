-- Create Tables
\i '/docker-entrypoint-initdb.d/user/create.sql'
\i '/docker-entrypoint-initdb.d/friends/create.sql'
\i '/docker-entrypoint-initdb.d/module/create.sql'
\i '/docker-entrypoint-initdb.d/locker/create.sql'
\i '/docker-entrypoint-initdb.d/trade/create.sql'
\i '/docker-entrypoint-initdb.d/hash/create.sql'

-- Insert Tables
\i '/docker-entrypoint-initdb.d/user/insert.sql'
\i '/docker-entrypoint-initdb.d/friends/insert.sql'
\i '/docker-entrypoint-initdb.d/module/insert.sql'
\i '/docker-entrypoint-initdb.d/locker/insert.sql'
\i '/docker-entrypoint-initdb.d/trade/insert.sql'

-- Triggers
\i '/docker-entrypoint-initdb.d/user/create-user-trigger.sql'
\i '/docker-entrypoint-initdb.d/user/delete-user-trigger.sql'
\i '/docker-entrypoint-initdb.d/friends/insert-friend-trigger.sql'
\i '/docker-entrypoint-initdb.d/friends/update-friend-trigger.sql'
\i '/docker-entrypoint-initdb.d/locker/insert-locker-trigger.sql'
\i '/docker-entrypoint-initdb.d/module/insert-module-trigger.sql'
\i '/docker-entrypoint-initdb.d/trade/insert-trade-trigger.sql'
\i '/docker-entrypoint-initdb.d/trade/update-trade-trigger.sql'