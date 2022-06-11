package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUseCaseTest {

    QuestionRepository repository;
    CreateUseCase useCase;

    @BeforeEach
    public void setup() {
        MapperUtils mapper = new MapperUtils();
        repository = mock(QuestionRepository.class);
        useCase = new CreateUseCase(mapper, repository);
    }

    @Test
    @DisplayName("Save a question")
    void saveAQuestion() {
        var question = new Question();
        question.setId("1");
        question.setUserId("xxxx-xxxx");
        question.setType("tech");
        question.setCategory("software");
        question.setQuestion("¿Que es java?");
        when(repository.save(question)).thenReturn(Mono.just(question));

        StepVerifier.create(useCase.apply(new QuestionDTO(question.getId(),
                        question.getUserId(),
                        question.getQuestion(),
                        question.getType(),
                        question.getCategory()
                ))
        ).expectNextMatches(questionEx -> {
            assert questionEx.equals(question.getId());
            return true;
        }).verifyComplete();

        verify(repository).save(question);
    }
}