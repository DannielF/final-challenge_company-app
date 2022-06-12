package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtils {

    Logger log = LoggerFactory.getLogger("Mapper");

    public Function<AnswerDTO, Answer> mapperToAnswer() {
        return updateAnswer -> {
            var answer = new Answer();
            answer.setPosition(updateAnswer.getPosition());
            answer.setQuestionId(updateAnswer.getQuestionId());
            answer.setUserId(updateAnswer.getUserId());
            answer.setAnswer(updateAnswer.getAnswer());
            answer.setDate(updateAnswer.getDate());
            return answer;
        };
    }

    public Function<QuestionDTO, Question> mapperToQuestion(String id) {
        return updateQuestion -> {
            var question = new Question();
            question.setId(id);
            question.setUserId(updateQuestion.getUserId());
            question.setQuestion(updateQuestion.getQuestion());
            question.setType(updateQuestion.getType());
            question.setCategory(updateQuestion.getCategory());
            question.setEmail(updateQuestion.getEmail());
            log.info(" QuestionDto to entity -> {}", question.getEmail());
            log.info(" QuestionDto to entity updateQuestion -> {}", updateQuestion.getEmail());
            return question;
        };
    }

    public Function<Question, QuestionDTO> mapEntityToQuestion() {
        return entity -> {
            log.info(" entity to DTO -> {}", entity.getEmail());
             return new QuestionDTO(
                    entity.getId(),
                    entity.getUserId(),
                    entity.getQuestion(),
                    entity.getType(),
                    entity.getCategory(),
                    entity.getEmail());
        };
    }

    public Function<Answer, AnswerDTO> mapEntityToAnswer() {
        return entity -> new AnswerDTO(
                entity.getId(),
                entity.getQuestionId(),
                entity.getUserId(),
                entity.getAnswer(),
                entity.getPosition(),
                entity.getDate()
        );
    }
}
