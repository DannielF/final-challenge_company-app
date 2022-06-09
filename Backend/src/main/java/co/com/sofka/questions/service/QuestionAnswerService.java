package co.com.sofka.questions.service;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.QuestionRepository;
import co.com.sofka.questions.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class QuestionAnswerService {

    private final QuestionRepository repository;
    private final MapperUtils mapper;

    public QuestionAnswerService(QuestionRepository repository, MapperUtils mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Flux<QuestionDTO> list() {
        repository.findAll().subscribe(q -> log.info("List all questions" + q));
        return repository.findAll()
                .map(mapper.mapEntityToQuestion());
    }

    public Flux<QuestionDTO> getOwnerAllById(String id) {
        repository.findAllByUserId(id).subscribe(q -> log.info("Get all user's questions" + q));
        return repository.findAllByUserId(id)
                .switchIfEmpty(Flux.error(new Throwable("UserId not found")))
                .map(mapper.mapEntityToQuestion());
    }

    public Mono<QuestionDTO> getQuestionById(String id) {
        repository.findById(id).subscribe(q -> log.info("Get question by its id" + q));
        return repository.findById(id).map(mapper.mapEntityToQuestion());
    }

    public Mono<QuestionDTO> createQuestion(QuestionDTO qDTO) {
        var question = mapper.mapperToQuestion().apply(qDTO);
        log.info("Question to save {}", question);
        return repository.save(question).map(mapper.mapEntityToQuestion());

    }

    public Mono<QuestionDTO> addAnswer(AnswerDTO answerDTO) {
        var answer = mapper.mapperToAnswer().apply(answerDTO);
        var question = repository
                .findById(answerDTO.questionId())
                .map(q -> {
                    answer.setId("ans" + q.userId());
                    q.listAnswers().add(answer);
                    return q;
                }).block();
        log.info("Question to add answer {}", question);
        return repository.save(question).map(mapper.mapEntityToQuestion());

    }

    public Mono<Void> deleteQuestionById(String id) {
        var question = repository.findById(id)
                .switchIfEmpty(Mono.error( new IllegalArgumentException("Question id not found")));
        return repository.delete(question.block());
    }
}
