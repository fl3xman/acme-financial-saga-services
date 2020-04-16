--liquibase formatted sql
--changeset author:fl3xman
INSERT INTO account (
    id,
    beneficiary_value,
    beneficiary_type
) VALUES (
    '123e4567-e89b-12d3-a456-426655440001',
    'AT483200000000000001',
    'IBAN'
), (
    '123e4567-e89b-12d3-a456-426655440002',
    'AT483200000000000002',
    'IBAN'
);