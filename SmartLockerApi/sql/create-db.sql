-- Create Tables
\i 'sql/user/create.sql'
\i 'sql/friends/create.sql'
\i 'sql/module/create.sql'
\i 'sql/locker/create.sql'
\i 'sql/trade/create.sql'
\i 'sql/hash/create.sql'

-- Insert Tables
\i 'sql/user/insert.sql'
\i 'sql/friends/insert.sql'
\i 'sql/module/insert.sql'
\i 'sql/locker/insert.sql'
\i 'sql/trade/insert.sql'

-- Triggers
\i 'sql/user/create-user-trigger.sql'
\i 'sql/user/delete-user-trigger.sql'
\i 'sql/friends/insert-friend-trigger.sql'
\i 'sql/friends/update-friend-trigger.sql'
\i 'sql/locker/insert-locker-trigger.sql'
\i 'sql/module/insert-module-trigger.sql'
\i 'sql/trade/insert-trade-trigger.sql'
\i 'sql/trade/update-trade-trigger.sql'