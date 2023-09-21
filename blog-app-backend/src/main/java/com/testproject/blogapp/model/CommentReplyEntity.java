package com.testproject.blogapp.model;


import com.testproject.blogapp.utils.Constants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@Table(name = "comment_reply", schema = Constants.SCHEMA_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentReplyEntity {
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
//    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private CommentEntity commentByCommentId;

}
