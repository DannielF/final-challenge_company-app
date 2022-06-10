package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class AddAnswerUseCaseTest {

    QuestionRepository questionRepository;
    AnswerRepository answerRepository;
    AddAnswerUseCase addAnswerUseCase;
    GetUseCase getUseCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        answerRepository = mock(AnswerRepository.class);
        questionRepository = mock(QuestionRepository.class);
        getUseCase = new GetUseCase(mapperUtils, questionRepository, answerRepository);
        addAnswerUseCase = new AddAnswerUseCase(mapperUtils, getUseCase, answerRepository);
    }

    @Test
    @DisplayName("Test for add an answer to a question")
    public void addAnswerUseCase() {

        var question = new QuestionDTO();
        question.setId("1");
        question.setUserId("1");
        question.setType("tech");
        question.setCategory("software");
        question.setQuestion("¿Que es java?");
        question.setAnswers(List.of(new AnswerDTO("1234", "1", "1", "answer", Instant.now(), Instant.now())));

        AnswerDTO answerDTO = new AnswerDTO("1234", "1", "1", "answer", Instant.now(), Instant.now());

        when(addAnswerUseCase.apply(answerDTO)).thenReturn(Mono.just(question));


        StepVerifier.create(addAnswerUseCase.apply(answerDTO))
                .expectNextMatches(questionDTO -> {
                    question.setId("1");
                    question.setUserId("1");
                    question.setType("tech");
                    question.setCategory("software");
                    question.setQuestion("¿Que es java?");
                    question.setAnswers(List.of(new AnswerDTO("1234", "1", "1", "answer", Instant.now(), Instant.now())));
                    return true;
                })
                .verifyComplete();

        verify(answerRepository).findAll();
    }
}