--liquibase formatted sql
--changeset author:fl3xman
CREATE TABLE payment(
    id                      uuid PRIMARY KEY NOT NULL,
    account_id              uuid NOT NULL,
    transaction_currency    varchar(3) NOT NULL,
    transaction_amount      decimal(9,2) NOT NULL,
    beneficiary_value       varchar(255) NOT NULL,
    beneficiary_type        varchar(60) NOT NULL,
    status                  varchar(60) NOT NULL DEFAULT 'PENDING',
    reason                  varchar(255),
    created_at              timestamp NOT NULL DEFAULT NOW(),
    modified_at             timestamp
    );
CREATE TABLE outbox(
    id              uuid PRIMARY KEY NOT NULL,
    aggregate_id    uuid NOT NULL,
    topic           varchar(255) NOT NULL,
    payload         text NOT NULL,
    created_at      timestamp NOT NULL DEFAULT NOW()
    );