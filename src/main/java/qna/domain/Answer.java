package qna.domain;

import qna.NotFoundException;
import qna.UnAuthorizedException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Answer extends DateHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;
    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @Lob
    private String contents;
    @Column
    private boolean deleted = false;

    protected Answer() {}

    public Answer(User writer, Question question, String contents) {
        this(null, writer, question, contents);
    }

    public Answer(Long id, User writer, Question question, String contents) {
        this.id = id;

        if (Objects.isNull(writer)) {
            throw new UnAuthorizedException();
        }

        if (Objects.isNull(question)) {
            throw new NotFoundException();
        }

        this.writer = writer;
        this.question = question;
        this.contents = contents;
    }

    public boolean isOwner(User writer) {
        return this.writer.equals(writer);
    }

    public void toQuestion(Question question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(final User writer) {
        this.writer = writer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(final Question question) {
        this.question = question;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", writer.id=" + writer.getId() +
                ", question.id=" + question.getId() +
                ", contents='" + contents + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
