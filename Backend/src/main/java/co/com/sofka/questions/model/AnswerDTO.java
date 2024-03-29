package co.com.sofka.questions.model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class AnswerDTO {

    private String id;
    @NotBlank(message = "There must be an id for this answer")
    private String userId;
    @NotBlank
    private String questionId;
    @NotBlank
    private String answer;
    @Max(5)
    @Min(1)
    @NotNull
    private Integer position;
    private Instant date;

    public AnswerDTO() {

    }

    public AnswerDTO(String id,
                     @NotBlank String questionId,
                     @NotBlank String userId,
                     @NotBlank String answer,
                     @NotNull Integer position,
                     Instant date) {
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
        this.position = position;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getPosition() {
        return Optional.ofNullable(position).orElse(1);
    }

    public void setPosition(Integer position) {
        this.position = position;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerDTO answerDTO = (AnswerDTO) o;
        return Objects.equals(userId, answerDTO.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "AnswerDTO {" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", questionId='" + questionId + '\'' +
                ", answer='" + answer + '\'' +
                ", position=" + position +
                ", date=" + date +
                '}';
    }
}
