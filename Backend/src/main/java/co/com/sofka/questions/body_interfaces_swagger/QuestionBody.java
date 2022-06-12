package co.com.sofka.questions.body_interfaces_swagger;

import javax.validation.constraints.NotBlank;

public class QuestionBody {

    @NotBlank
    private String userId;
    @NotBlank
    private String question;
    @NotBlank
    private String type;
    @NotBlank
    private String category;
    @NotBlank
    private String email;

    public QuestionBody(String userId, String question, String type, String category, String email) {
        this.userId = userId;
        this.question = question;
        this.type = type;
        this.category = category;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "QuestionBody {" +
                "userId='" + userId + '\'' +
                ", question='" + question + '\'' +
                ", type='" + type + '\'' +
                ", category='" + category + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
