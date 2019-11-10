CREATE DATABASE IF NOT EXISTS tour_agency;
USE tour_agency;
CREATE TABLE IF NOT EXISTS users (
    passport VARCHAR(9) PRIMARY KEY NOT NULL,
    name VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    phone_number VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS tours (
    id INT(4) PRIMARY KEY NOT NULL,
    name VARCHAR(32) UNIQUE NOT NULL,
    departure_time VARCHAR(12) NOT NULL,
    arrival_time VARCHAR(12) NOT NULL,
    transport VARCHAR(5) NOT NULL,
    type VARCHAR(8) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price FLOAT,
    image VARCHAR(64) NOT NULL
);
CREATE TABLE IF NOT EXISTS orders (
    id INT(4) PRIMARY KEY NOT NULL,
    user_passport VARCHAR(9) NOT NULL,
    FOREIGN KEY (user_passport) REFERENCES users (passport) ON DELETE CASCADE,
    tour_id INT(4) NOT NULL,
    FOREIGN KEY (tour_id) REFERENCES tours (id) ON DELETE RESTRICT
);
CREATE TABLE IF NOT EXISTS countries (
    name VARCHAR(64) PRIMARY KEY NOT NULL,
    description VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS hotels (
    id INT(4) PRIMARY KEY NOT NULL,
    country_name VARCHAR(64) NOT NULL,
    FOREIGN KEY (country_name) REFERENCES countries (name) ON DELETE RESTRICT,
    stars INT(4),
    name VARCHAR(32) NOT NULL,
    web_site VARCHAR(64) NOT NULL,
    description VARCHAR(255) NOT NULL,
    address VARCHAR(128) NOT NULL
);
CREATE TABLE IF NOT EXISTS tour_hotels (
    tour_id INT(4) NOT NULL,
    FOREIGN KEY (tour_id) REFERENCES tours (id) ON DELETE CASCADE,
    hotel_id INT(4) NOT NULL,
    FOREIGN KEY (hotel_id) REFERENCES hotels (id) ON DELETE RESTRICT
);
CREATE TABLE IF NOT EXISTS tour_countries (
    country_name VARCHAR(64) NOT NULL,
    FOREIGN KEY (country_name) REFERENCES countries (name) ON DELETE RESTRICT,
    tour_id INT(4) NOT NULL,
    FOREIGN KEY (tour_id) REFERENCES tours (id) ON DELETE CASCADE
);