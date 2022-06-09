package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtils {

    public Function<AnswerDTO, Answer> mapperToAnswer() {
        return updateAnswer -> {
            var answer = new Answer();
            answer.setPosition(updateAnswer.position());
            answer.setQuestionId(updateAnswer.questionId());
            answer.setUserId(updateAnswer.userId());
            answer.setAnswer(updateAnswer.answer());
            return answer;
        };
    }

    public Function<QuestionDTO, Question> mapperToQuestion(String id) {
        return updateQuestion -> {
            var question = new Question();
            question.setId(id);
            question.setUserId(updateQuestion.userId());
            question.setQuestion(updateQuestion.question());
            question.setType(updateQuestion.type());
            question.setCategory(updateQuestion.category());
            return question;
        };
    }

    public Function<Question, QuestionDTO> mapEntityToQuestion() {
        return entity -> new QuestionDTO(
                entity.id(),
                entity.userId(),
                entity.question(),
                entity.type(),
                entity.category()
        );
    }

    public Function<Answer, AnswerDTO> mapEntityToAnswer() {
        return entity -> new AnswerDTO(
                entity.id(),
                entity.userId(),
                entity.answer(),
                entity.created()
        );
    }
}
