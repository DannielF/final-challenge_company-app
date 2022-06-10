package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.CreateUseCase;
import co.com.sofka.questions.usecases.GetUseCase;
import co.com.sofka.questions.usecases.ListUseCase;
import co.com.sofka.questions.usecases.OwnerListUseCase;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static reactor.core.publisher.Mono.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuestionRouterTest {

    @Autowired
    public QuestionRouter router;

    @MockBean
    public ListUseCase listService;
    @MockBean
    public OwnerListUseCase ownerListUseCase;
    @MockBean
    public CreateUseCase createUseCase;
    @MockBean
    public GetUseCase getUseCase;

    @Test
    void getAllQuestions() {
        //Arrange
        Flux<QuestionDTO> questionDTOFlux = Flux.just(new QuestionDTO(), new QuestionDTO());
        given(listService.get()).willReturn(questionDTOFlux);
        //Act
        WebTestClient client = WebTestClient.bindToRouterFunction(router.getAllQuestions(listService)).build();
        //Assert
        client.get().uri("/getAllQuestions").exchange().expectStatus().isOk()
                .returnResult(QuestionDTO.class).getResponseBody();
    }

    @Test
    void getAllQuestionsByUserId() {
        //Arrange
        Flux<QuestionDTO> questionDTOFlux = Flux.just(new QuestionDTO());
        String userId = "1";
        given(ownerListUseCase.apply(userId)).willReturn(questionDTOFlux);
        //Act
        WebTestClient client = WebTestClient.bindToRouterFunction(router.getAllQuestionsByUserId(ownerListUseCase)).build();
        //Assert
        client.get().uri("/getAllQuestions/{userId}",1).exchange().expectStatus().isOk()
                .returnResult(QuestionDTO.class).getResponseBody();
    }

    @Test
    void createQuestion() {
        //Arrange
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId("1");
        Mono<String> questionId = Mono.just("1");
        given(createUseCase.apply(questionDTO)).willReturn(questionId);
        //Act
        WebTestClient client = WebTestClient.bindToRouterFunction(router.createQuestion(createUseCase)).build();
        //Assert
        client.post().uri("/createQuestion").exchange().expectStatus().isOk()
                .returnResult(QuestionDTO.class).getResponseBody();
    }


    @Test
    void getQuestion() {
        //Arrange
        Mono<QuestionDTO> questionDTOMono = Mono.just(new QuestionDTO());
        String userId = "1";
        given(getUseCase.apply(userId)).willReturn(questionDTOMono);
        //Act
        WebTestClient client = WebTestClient.bindToRouterFunction(router.getQuestion(getUseCase)).build();
        //Assert
        client.get().uri("/getQuestion/{id}",1).exchange().expectStatus().isOk()
                .returnResult(QuestionDTO.class).getResponseBody();
    }

    @Test
    void addAnswer() {
    }

    @Test
    void updateQuestion() {
    }

    @Test
    void deleteQuestionById() {
    }

    @Test
    void deleteAnswerById() {
    }
}