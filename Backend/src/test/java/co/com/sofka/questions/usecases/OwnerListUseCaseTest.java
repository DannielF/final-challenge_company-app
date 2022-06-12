package co.com.sofka.questions.usecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class OwnerListUseCaseTest {

    @MockBean
    QuestionRepository questionRepository;
    @MockBean
    OwnerListUseCase ownerListUseCase;

    @Test
    @DisplayName("Get questions by user id")
    void getQuestionsByUserId() {

        var questionDTO = new QuestionDTO();
        questionDTO.setId("1");
        questionDTO.setUserId("xxxx-xxxx");
        questionDTO.setType("tech");
        questionDTO.setCategory("software");
        questionDTO.setQuestion("¿Que es java?");

        when(ownerListUseCase.apply(Mockito.anyString())).thenReturn(Flux.just(questionDTO));

        var fluxQuestions = ownerListUseCase.apply("xxxx-xxxx").collectList().block();

        assert fluxQuestions != null;
        assertEquals(fluxQuestions.get(0).getUserId(), questionDTO.getUserId());
    }

}