package com.testproject.blogapp.model;

import com.testproject.blogapp.utils.Constants;
import jakarta.persistence.*;

@Entity
//@Table(name = "reported_comment", schema = Constants.SCHEMA_NAME)
public class ReportedCommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
//    @Column(name = "id")
    private Integer id;
    @Basic
//    @Column(name = "reason")
    private String reason;
    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userByUserId;
    @ManyToOne
//    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private PostEntity postByPostId;
    @ManyToOne
//    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private CommentEntity commentByCommentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportedCommentEntity that = (ReportedCommentEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reason != null ? !reason.equals(that.reason) : that.reason != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        return result;
    }

    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    public PostEntity getPostByPostId() {
        return postByPostId;
    }

    public void setPostByPostId(PostEntity postByPostId) {
        this.postByPostId = postByPostId;
    }

    public CommentEntity getCommentByCommentId() {
        return commentByCommentId;
    }

    public void setCommentByCommentId(CommentEntity commentByCommentId) {
        this.commentByCommentId = commentByCommentId;
    }
}
