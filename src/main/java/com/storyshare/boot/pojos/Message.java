package com.storyshare.boot.pojos;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MESSAGE")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "long")
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column
    @Type(type = "string")
    @Access(AccessType.PROPERTY)
    private String text;

    @Column(columnDefinition = "DATETIME(3)")
    @Access(AccessType.PROPERTY)
    private LocalDateTime date;

    @Column(name = "DELETED_BY_SENDER", columnDefinition = "bit(1) DEFAULT false")
    @Type(type = "boolean")
    @Access(AccessType.PROPERTY)
    private Boolean deletedBySender;

    @Column(name = "DELETED_BY_RECEIVER", columnDefinition = "bit(1) DEFAULT false")
    @Type(type = "boolean")
    @Access(AccessType.PROPERTY)
    private Boolean deletedByReceiver;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "SENDER_ID", foreignKey = @ForeignKey(name = "SENDER_ID_FK"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private User sender;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RECEIVER_ID", foreignKey = @ForeignKey(name = "RECEIVER_ID_FK"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private User receiver;

    public Message(String text, LocalDateTime date, User sender, User receiver) {
        this.text = text;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.deletedByReceiver = false;
        this.deletedBySender = false;
    }
}
