--liquibase formatted sql
--changeset author:fl3xman
CREATE TABLE payment(
    id uuid PRIMARY KEY NOT NULL
    );