package se.experis.academy.session.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a post on a notice board
 */
@Entity(name = "notices")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity = User.class)
    private User author;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<Comment>();

    @JsonGetter("comments")
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    /**
     * Constructor
     * @return Notice
     */
    public String getDate() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    /**
     * Gets the date as an date object
     * @return date object
     */
    public Date getDateOriginal() {
        return date;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public Notice addComment(Comment comment) {
        comments.add(comment);
        return this;
    }

    public Notice setAuthor(User author) {
        this.author = author;
        return this;
    }

    public Notice setContent(String content) {
        this.content = content;
        return this;
    }

    public Notice setDate(Date date) {
        this.date = date;
        return this;
    }
}
