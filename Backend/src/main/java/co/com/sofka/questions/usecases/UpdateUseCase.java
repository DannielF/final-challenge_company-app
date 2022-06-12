package co.com.sofka.questions.usecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import co.com.sofka.questions.reposioties.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Objects;


@Service
@Validated
public class UpdateUseCase implements SaveQuestion {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;

    @Autowired
    ReactiveMongoTemplate mongoTemplate;

    public UpdateUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
        this.answerRepository = answerRepository;
    }

    public Mono<AnswerDTO> updateAnswer(AnswerDTO dto) {
        //Objects.requireNonNull(dto.getId(), "Id is required");
        Query query = new Query().addCriteria(Criteria.where("_id").is(dto.getId()));
        Update update = new Update().set("answer", dto.getAnswer()).set("position", dto.getPosition());


        dto.setDate(Instant.now());
        return mongoTemplate.findAndModify(query, update, Answer.class)
                .map(mapperUtils.mapEntityToAnswer());
    }

    @Override
    public Mono<String> apply(QuestionDTO dto) {
        Objects.requireNonNull(dto.getId(), "Id of the question is required");
        return questionRepository
                .save(mapperUtils.mapperToQuestion(dto.getId()).apply(dto))
                .map(Question::getId);
    }
}
