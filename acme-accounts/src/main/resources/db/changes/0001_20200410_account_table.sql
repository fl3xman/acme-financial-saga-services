--liquibase formatted sql
--changeset author:fl3xman
CREATE TABLE account(
    id uuid PRIMARY KEY NOT NULL
    );