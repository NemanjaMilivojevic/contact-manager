CREATE TABLE if NOT EXISTS contact_type(
   id BIGSERIAL PRIMARY KEY,
   type VARCHAR(10)
);

CREATE TABLE if NOT EXISTS users(
 id BIGSERIAL PRIMARY KEY,
 email VARCHAR(50) unique,
 password VARCHAR(250),
 user_role VARCHAR(10)
);

CREATE TABLE if NOT EXISTS contacts(
 id BIGSERIAL PRIMARY KEY,
 first_name VARCHAR(40),
 last_name VARCHAR(40),
 address VARCHAR(50),
 phone_number VARCHAR(20),
 user_id BIGSERIAL REFERENCES users(id),
 contact_type_id BIGSERIAL REFERENCES contact_type(id)
);