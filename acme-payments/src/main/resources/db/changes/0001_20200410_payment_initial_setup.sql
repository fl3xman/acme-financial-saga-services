--liquibase formatted sql
--changeset author:fl3xman
CREATE TABLE payment(
    id                      uuid PRIMARY KEY NOT NULL,
    transaction_currency    varchar(3) NOT NULL,
    transaction_amount      decimal(9,2) NOT NULL,
    status                  varchar(50) NOT NULL DEFAULT 'pending'
    );
CREATE TABLE outbox(
    id              uuid PRIMARY KEY NOT NULL,
    aggregate_id    uuid NOT NULL,
    topic           varchar(255) NOT NULL,
    event           varchar(255) NOT NULL,
    payload         text NOT NULL
    );