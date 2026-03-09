CREATE TABLE forums (
                        id UUID PRIMARY KEY,
                        name VARCHAR(120) NOT NULL,
                        description VARCHAR(500) NOT NULL,
                        type VARCHAR(20) NOT NULL,
                        course_id UUID NULL,
                        created_by UUID NOT NULL,
                        active BOOLEAN NOT NULL DEFAULT TRUE,
                        created_at TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP NOT NULL,

                        CONSTRAINT fk_forum_created_by
                            FOREIGN KEY (created_by)
                                REFERENCES users(id)
                                ON DELETE RESTRICT
);


CREATE INDEX idx_forum_active ON forums(active);
CREATE INDEX idx_forum_type ON forums(type);
CREATE INDEX idx_forum_created_by ON forums(created_by);