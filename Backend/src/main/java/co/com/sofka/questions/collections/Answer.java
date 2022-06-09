package co.com.sofka.questions.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
public class Answer {
    @Id
    private String id;
    private String userId;
    private String questionId;
    private String answer;
    private Integer position;
    private Instant created;
    private Instant updated;

    public Instant created() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant updated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Integer position() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String id() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String userId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String questionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String answer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
