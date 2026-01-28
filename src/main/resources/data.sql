INSERT IGNORE INTO app_users(username, password, name, created_at)
VALUES ('admin','$2a$12$txhRDxqX4U4TajQm/bEAwuLegm4trkcoN.xTTHwq7uRf/BAWMP/4S','admin', NOW());

INSERT INTO user_roles (user_id, role)
SELECT id, 'ADMIN'
FROM app_users
WHERE username = 'admin';