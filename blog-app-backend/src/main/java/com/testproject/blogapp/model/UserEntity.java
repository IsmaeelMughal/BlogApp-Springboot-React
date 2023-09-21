package com.testproject.blogapp.model;

import com.testproject.blogapp.utils.Constants;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
//@Table(name = "user", schema = Constants.SCHEMA_NAME)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
//    @Column(name = "id")
    private Integer id;
    @Basic
//    @Column(name = "name")
    private String name;
    @Basic
//    @Column(name = "email")
    private String email;
    @Basic
//    @Column(name = "password")
    private String password;
    @Basic
//    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Basic
//    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserAccountStatus status;
    @Basic
//    @Column(name = "otp")
    private Integer otp;
    @Basic
//    @Column(name = "otp_expiration")
    private LocalDateTime otpExpiration;

    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
    private Set<CommentEntity> commentsById;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
    private Set<CommentLikeEntity> commentLikesById;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
    private Set<PostEntity> postsById;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
    private Set<PostLikeEntity> postLikesById;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
    private Set<ReportedCommentEntity> reportedCommentsById;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
    private Set<ReportedPostEntity> reportedPostsById;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
    private Set<SuggestionEntity> suggestionsById;
    @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
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

    public Set<PostEntity> getPostsById() {
        return postsById;
    }

    public void setPostsById(Set<PostEntity> postsById) {
        this.postsById = postsById;
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
