package com.forum.forumhub.forums.entity;

import com.forum.forumhub.user.entity.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "forum_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "forum_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForumMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    private ForumsModel forum;

    private LocalDateTime joinedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ForumsModel getForum() {
        return forum;
    }

    public void setForum(ForumsModel forum) {
        this.forum = forum;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}