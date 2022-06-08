package co.com.sofka.questions.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Question {
    @Id
    private String id;
    private String userId;
    private String question;
    private String type;
    private String category;


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

    public String question() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String type() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String category() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
