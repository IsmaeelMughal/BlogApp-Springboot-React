package com.testproject.blogapp.model;

import com.testproject.blogapp.utils.Constants;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
//@Table(name = "post", schema = Constants.SCHEMA_NAME)
public class PostEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
//    @Column(name = "id")
    private Integer id;
    @Basic
//    @Column(name = "heading")
    private String title;
    @Basic
//    @Column(name = "content", length = 2147483647)
    private String content;
    @Basic
//    @Column(name = "image")
    private String image;
    @Basic
//    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;
    @Basic
//    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(mappedBy = "postByPostId", cascade = CascadeType.ALL)
    private Set<CommentEntity> commentsById;
    @OneToMany(mappedBy = "postByPostId", cascade = CascadeType.ALL)
    private Set<CommentLikeEntity> commentLikesById;
    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userByUserId;
    @OneToMany(mappedBy = "postByPostId", cascade = CascadeType.ALL)
    private Set<PostLikeEntity> postLikesById;
    @OneToMany(mappedBy = "postByPostId", cascade = CascadeType.ALL)
    private Set<ReportedCommentEntity> reportedCommentsById;
    @OneToMany(mappedBy = "postByPostId", cascade = CascadeType.ALL)
    private Set<ReportedPostEntity> reportedPostsById;
    @OneToMany(mappedBy = "postByPostId", cascade = CascadeType.ALL)
    private Set<SuggestionEntity> suggestionsById;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PostStatus getStatus() {
        return postStatus;
    }

    public void setStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostEntity that = (PostEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    public Set<CommentEntity> getCommentsById() {
        return commentsById;
    }

    public void setCommentsById(Set<CommentEntity> commentsById) {
        this.commentsById = commentsById;
    }

    public Set<CommentLikeEntity> getCommentLikesById() {
        return commentLikesById;
    }

    public void setCommentLikesById(Set<CommentLikeEntity> commentLikesById) {
        this.commentLikesById = commentLikesById;
    }

    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    public Set<PostLikeEntity> getPostLikesById() {
        return postLikesById;
    }

    public void setPostLikesById(Set<PostLikeEntity> postLikesById) {
        this.postLikesById = postLikesById;
    }

    public Set<ReportedCommentEntity> getReportedCommentsById() {
        return reportedCommentsById;
    }

    public void setReportedCommentsById(Set<ReportedCommentEntity> reportedCommentsById) {
        this.reportedCommentsById = reportedCommentsById;
    }

    public Set<ReportedPostEntity> getReportedPostsById() {
        return reportedPostsById;
    }

    public void setReportedPostsById(Set<ReportedPostEntity> reportedPostsById) {
        this.reportedPostsById = reportedPostsById;
    }

    public Set<SuggestionEntity> getSuggestionsById() {
        return suggestionsById;
    }

    public void setSuggestionsById(Set<SuggestionEntity> suggestionsById) {
        this.suggestionsById = suggestionsById;
    }
}
