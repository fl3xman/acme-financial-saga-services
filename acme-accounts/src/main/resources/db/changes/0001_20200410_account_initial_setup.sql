--liquibase formatted sql
--changeset author:fl3xman
CREATE TABLE account(
    id uuid                 PRIMARY KEY NOT NULL,
    beneficiary_value       varchar(255) NOT NULL,
    beneficiary_type        varchar(60) NOT NULL
    );
CREATE TABLE account_operation(
    id                      uuid PRIMARY KEY NOT NULL,
    account_id              uuid,
    FOREIGN KEY (account_id) REFERENCES account (id),
    transaction_currency    varchar(3) NOT NULL,
    transaction_amount      decimal(9,2) NOT NULL,
    beneficiary_value       varchar(255) NOT NULL,
    beneficiary_type        varchar(60) NOT NULL,
    type                    varchar(60) NOT NULL,
    created_at              timestamp NOT NULL DEFAULT NOW()
    );
CREATE TABLE outbox(
    id              uuid PRIMARY KEY NOT NULL,
    aggregate_id    uuid NOT NULL,
    topic           varchar(255) NOT NULL,
    payload         text NOT NULL,
    created_at      timestamp NOT NULL DEFAULT NOW()
    );
