package com.storyshare.boot.pojos;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "long")
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    private String name;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    private String email;

    @Column
    @Type(type = "materialized_clob")
    @Access(AccessType.PROPERTY)
    private String avatar;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    private String password;

    @Column(columnDefinition = "VARCHAR(5) DEFAULT 'user'")
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    private String role;

    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'active'")
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    private String status;

    @OneToMany(
            mappedBy = "sender",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Message> senderMessages = new ArrayList<>();

    @OneToMany(
            mappedBy = "receiver",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Message> receiverMessages = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Post> posts = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Comment> comments = new ArrayList<>();

    public User(String name, String email, String avatar, String password, String role) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.password = password;
        this.role = role;
        this.status = "active";
    }
}
