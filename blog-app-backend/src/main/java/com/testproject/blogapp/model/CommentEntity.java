package com.testproject.blogapp.model;

import com.testproject.blogapp.utils.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Set;

@Entity
//@Table(name = "comment", schema = Constants.SCHEMA_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
//    @Column(name = "id")
    private Integer id;
    @Basic
//    @Column(name = "content", length = 1000)
    private String content;
    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userByUserId;
    @ManyToOne
//    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private PostEntity postByPostId;
    @OneToMany(mappedBy = "commentByCommentId", cascade = CascadeType.ALL)
    private Set<CommentLikeEntity> commentLikesById;
    @OneToMany(mappedBy = "commentByCommentId", cascade = CascadeType.ALL)
    private Set<ReportedCommentEntity> reportedCommentsById;
    @OneToMany(mappedBy = "commentByCommentId", cascade = CascadeType.ALL)
    private Set<CommentReplyEntity> commentRepliesById;

    public Set<CommentReplyEntity> getCommentRepliesById() {
        return commentRepliesById;
    }

    public void setCommentRepliesById(Set<CommentReplyEntity> commentRepliesById) {
        this.commentRepliesById = commentRepliesById;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentEntity that = (CommentEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
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

    public Set<CommentLikeEntity> getCommentLikesById() {
        return commentLikesById;
    }

    public void setCommentLikesById(Set<CommentLikeEntity> commentLikesById) {
        this.commentLikesById = commentLikesById;
    }

    public Collection<ReportedCommentEntity> getReportedCommentsById() {
        return reportedCommentsById;
    }

    public void setReportedCommentsById(Set<ReportedCommentEntity> reportedCommentsById) {
        this.reportedCommentsById = reportedCommentsById;
    }
}
