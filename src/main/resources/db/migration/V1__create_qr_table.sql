create type qr_type as enum ('simpleQr', 'qrWithStatistics', 'qrList');

create table qr_codes (
    qr_code_id UUID primary key,
    user_id UUID,
    type qr_type,
    qr_url TEXT,
    target_url TEXT,
    created_at timestamp
);

CREATE TABLE qr_code_data (
    qr_code_data_id SERIAL PRIMARY KEY,
    qr_code_id UUID UNIQUE REFERENCES qr_codes(qr_code_id) ON DELETE CASCADE,
    content JSONB
);