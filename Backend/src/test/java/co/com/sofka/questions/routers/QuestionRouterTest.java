package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.ListUseCase;
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

    @Test
    void getAllQuestions() {

        Flux<QuestionDTO> questionDTOFlux = Flux.just(new QuestionDTO(), new QuestionDTO());
        given(listService.get()).willReturn(questionDTOFlux);
        WebTestClient client = WebTestClient.bindToRouterFunction(router.getAllQuestions(listService)).build();

        client.get().uri("/getAllQuestions").exchange().expectStatus().isOk();

    }

    @Test
    void getAllQuestionsByUserId() {


    }

    @Test
    void createQuestion() {
    }

    @Test
    void getQuestion() {
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