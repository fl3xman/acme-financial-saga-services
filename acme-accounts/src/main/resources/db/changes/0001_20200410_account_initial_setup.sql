--liquibase formatted sql
--changeset author:fl3xman
CREATE TABLE account(
    id uuid         PRIMARY KEY NOT NULL
    );
CREATE TABLE outbox(
    id              uuid PRIMARY KEY NOT NULL,
    aggregate_id    uuid NOT NULL,
    topic           varchar(255) NOT NULL,
    payload         text NOT NULL
    );
