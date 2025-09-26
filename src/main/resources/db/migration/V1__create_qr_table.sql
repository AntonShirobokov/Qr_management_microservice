create type qr_type as enum ('simpleQr', 'qrWithStatistics', 'qrList');

create table qr_codes (
    qrcode_id UUID primary key,
    user_id UUID,
    type qr_type,
    target_url TEXT,
    created_at timestamp
);

create table list_items (
    list_items_id serial primary key,
    qrcode_id UUID references qr_codes(qrcode_id) on delete cascade,
    content text,
    created_at timestamp,
    updated_at timestamp
);