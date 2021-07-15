DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS account_roles;

CREATE TABLE IF NOT EXISTS accounts (
    id          bigserial       PRIMARY KEY,
    email       varchar(256)    UNIQUE NOT NULL,
    address     varchar(64)     NOT NULL,
    public_key  bytea           NOT NULL,
    password    varchar(128)    NOT NULL,
    last_login  timestamp,
    updated_on  timestamp,
    created_on  timestamp       NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
    id          serial          PRIMARY KEY,
    name        varchar(64)     UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS account_roles (
    account_id  int             NOT NULL,
    role_id     int             NOT NULL,
    updated_on  timestamp,
    created_on  timestamp       NOT NULL,
    PRIMARY KEY (account_id, role_id),
    FOREIGN KEY (account_id)    REFERENCES accounts    (id),
    FOREIGN KEY (role_id)       REFERENCES roles       (id)
);
