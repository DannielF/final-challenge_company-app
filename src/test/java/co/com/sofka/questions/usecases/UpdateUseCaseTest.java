package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UpdateUseCaseTest {

    @MockBean
    QuestionRepository questionRepository;
    @MockBean
    AnswerRepository answerRepository;
    @MockBean
    ReactiveMongoTemplate reactiveMongoTemplate;
    @MockBean
    UpdateUseCase updateUseCase;


    @Test
    @DisplayName("Update a question")
    void UpdateQuestion() {

        var question = new Question();
        question.setId("1");
        question.setUserId("xxxx-xxxx");
        question.setType("tech");
        question.setCategory("software");
        question.setQuestion("¿Que es java?");

        when(questionRepository.findById("1"))
                .thenReturn(Mono.just(question));

        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));

        var questionDto = new QuestionDTO(
                "1",
                "1234",
                "Tech",
                "Software",
                "¿Que es java?",
                "email");

        when(updateUseCase.apply(Mockito.any(QuestionDTO.class))).thenReturn(Mono.just(questionDto.getId()));

        var questionUpdated = updateUseCase.apply(questionDto).block();

        assertEquals(questionUpdated, questionDto.getId());
    }

    @Test
    @DisplayName("Update an answer by its id")
    void UpdateAnswerById() {

        Answer answer = new Answer();
        answer.setId("1234");
        answer.setQuestionId("1");
        answer.setAnswer("answer");
        answer.setPosition(1);
        answer.setDate(Instant.now());

        Answer answerUpdated = new Answer();
        answerUpdated.setId("1234");
        answerUpdated.setQuestionId("1");
        answerUpdated.setAnswer("answer updated");
        answerUpdated.setPosition(4);
        answerUpdated.setDate(Instant.now());

        when(answerRepository.save(Mockito.any(Answer.class))).thenReturn(Mono.just(answer));

        when(reactiveMongoTemplate.findAndModify(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.any()))
                .thenReturn(Mono.just(answerUpdated));


        Query query = new Query().addCriteria(Criteria.where("_id").is(answerUpdated.getId()));
        Update update = new Update().set("answer", answerUpdated.getAnswer())
                .set("position", answerUpdated.getPosition())
                .set("date", Instant.now());

        var answerToUpdate = reactiveMongoTemplate.findAndModify(query, update, Answer.class);
        var response = answerToUpdate.block();

        assert response != null;
        assertThat(response.getId()).isEqualTo(answerUpdated.getId());
        assertThat(response.getAnswer()).isEqualTo(answerUpdated.getAnswer());
    }

}