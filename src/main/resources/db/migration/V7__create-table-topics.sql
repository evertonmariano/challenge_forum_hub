CREATE TABLE topics (
                        id UUID PRIMARY KEY,

                        title VARCHAR(255) NOT NULL,
                        content TEXT NOT NULL,

                        type VARCHAR(30) NOT NULL,
                        status VARCHAR(30) NOT NULL,

                        forum_id UUID NOT NULL,
                        author_id UUID NOT NULL,

                        deleted BOOLEAN NOT NULL DEFAULT FALSE,

                        created_at TIMESTAMP NOT NULL,
                        updated_at TIMESTAMP,
                        deleted_at TIMESTAMP,

                        CONSTRAINT fk_topics_forum
                            FOREIGN KEY (forum_id)
                                REFERENCES forums(id)
                                ON DELETE CASCADE,

                        CONSTRAINT fk_topics_author
                            FOREIGN KEY (author_id)
                                REFERENCES users(id)
                                ON DELETE CASCADE
);