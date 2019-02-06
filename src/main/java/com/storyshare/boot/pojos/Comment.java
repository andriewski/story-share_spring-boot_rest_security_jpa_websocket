package com.storyshare.boot.pojos;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "long")
    @Access(AccessType.PROPERTY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID", foreignKey = @ForeignKey(name = "USER_ID_FK"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "POST_ID", foreignKey = @ForeignKey(name = "POST_ID_FK"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Post post;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    private String text;

    @Column(columnDefinition = "DATETIME(3)")
    @Access(AccessType.PROPERTY)
    private LocalDateTime date;

    public Comment(User user, Post post, String text, LocalDateTime date) {
        this.user = user;
        this.post = post;
        this.text = text;
        this.date = date;
    }
}