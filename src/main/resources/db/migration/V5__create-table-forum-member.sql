CREATE TABLE forum_members (
                               id UUID PRIMARY KEY,
                               user_id UUID NOT NULL,
                               forum_id UUID NOT NULL,
                               joined_at TIMESTAMP NOT NULL,

                               CONSTRAINT fk_member_user
                                   FOREIGN KEY (user_id) REFERENCES users(id),

                               CONSTRAINT fk_member_forum
                                   FOREIGN KEY (forum_id) REFERENCES forums(id),

                               CONSTRAINT uk_user_forum UNIQUE (user_id, forum_id)
);