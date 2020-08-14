package se.experis.academy.session.model;

import javax.persistence.*;

/**
 * Represents a comment
 */
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity = User.class)
    private User author;

    @Column(name = "text", nullable = false)
    private String text;

    public Comment(){}

    /**
     * Constructor
     * @param author
     * @param text
     */
    public Comment(User author, String text) {
        this.author = author;
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Comment setAuthor(User author) {
        this.author = author;
        return this;
    }
}
