INSERT IGNORE INTO app_users(username, password, name, roles, created_at)
VALUES ('admin','$2a$12$txhRDxqX4U4TajQm/bEAwuLegm4trkcoN.xTTHwq7uRf/BAWMP/4S','admin','["ADMIN"]', NOW());