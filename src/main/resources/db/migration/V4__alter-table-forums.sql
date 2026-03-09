ALTER TABLE forums
    ADD COLUMN pending_master_delete BOOLEAN DEFAULT FALSE;

ALTER TABLE forums
    ADD COLUMN requested_by_user_id UUID NULL;

-- Opcional: criar foreign key para UserModel
ALTER TABLE forums
    ADD CONSTRAINT fk_requested_by_user FOREIGN KEY (requested_by_user_id)
        REFERENCES users(id);