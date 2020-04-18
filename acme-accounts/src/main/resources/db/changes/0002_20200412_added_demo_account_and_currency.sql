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
INSERT INTO account_operation (
    id,
    account_id,
    transaction_id,
    transaction_type,
    transaction_amount,
    transaction_currency,
    beneficiary_value,
    beneficiary_type,
    type
) VALUES (
    '123e4567-e89b-12d3-a456-426655441001',
    '123e4567-e89b-12d3-a456-426655440001',
    '123e4567-e89b-12d3-a456-426655441101',
    'LOCAL_DEPOSIT',
    1000,
    'EUR',
    'AT483200000000000001',
    'IBAN',
    'DEPOSIT'
), (
    '123e4567-e89b-12d3-a456-426655442002',
    '123e4567-e89b-12d3-a456-426655440002',
    '123e4567-e89b-12d3-a456-426655442202',
    'LOCAL_DEPOSIT',
    1000,
    'EUR',
    'AT483200000000000002',
    'IBAN',
    'DEPOSIT'
);
