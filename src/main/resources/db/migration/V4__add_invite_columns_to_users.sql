ALTER TABLE users ALTER COLUMN password DROP NOT NULL;

ALTER TABLE users ADD COLUMN invite_token VARCHAR(255);
ALTER TABLE users ADD COLUMN invite_token_expiry TIMESTAMPTZ;

CREATE UNIQUE INDEX uk_users_invite_token ON users (invite_token) WHERE invite_token IS NOT NULL;
