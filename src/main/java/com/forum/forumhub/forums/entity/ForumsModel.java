package com.forum.forumhub.forums.entity;

import com.forum.forumhub.shared.utils.enums.ForumType;
import com.forum.forumhub.user.entity.UserModel;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "forums")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ForumsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ForumType type;

    @Column(name = "course_id")
    private UUID courseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserModel createdBy;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "pending_master_delete", nullable = false)
    private Boolean pendingMasterDelete = false;

    @ManyToOne
    @JoinColumn(name = "requested_by_user_id")
    private UserModel requestedByMasterDelete;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public ForumsModel(String name,
                      String description,
                      ForumType type,
                      UserModel createdBy) {

        this.name = name;
        this.description = description;
        this.type = type;
        this.createdBy = createdBy;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void activate() {
        this.active = true;
    }

    public void requestMasterDelete(UserModel user) {
        this.pendingMasterDelete = true;
        this.requestedByMasterDelete = user;
    }

    public void cancelMasterDelete() {
        this.pendingMasterDelete = false;
        this.requestedByMasterDelete = null;
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ForumType getType() {
        return type;
    }

    public void setType(ForumType type) {
        this.type = type;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public UserModel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserModel createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getPendingMasterDelete() {
        return pendingMasterDelete;
    }

    public void setPendingMasterDelete(Boolean pendingMasterDelete) {
        this.pendingMasterDelete = pendingMasterDelete;
    }

    public UserModel getRequestedByMasterDelete() {
        return requestedByMasterDelete;
    }

    public void setRequestedByMasterDelete(UserModel requestedByMasterDelete) {
        this.requestedByMasterDelete = requestedByMasterDelete;
    }
}
