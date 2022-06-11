package co.com.sofka.questions.usecases;

import co.com.sofka.questions.config.EmailServiceImpl;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.reposioties.AnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Validated
public class AddAnswerUseCase implements SaveAnswer {
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;
    private final GetUseCase getUseCase;

    @Autowired
    private EmailServiceImpl emailService;
    Logger log = LoggerFactory.getLogger("");

    public AddAnswerUseCase(MapperUtils mapperUtils, GetUseCase getUseCase, AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
        this.getUseCase = getUseCase;
        this.mapperUtils = mapperUtils;
    }

    public Mono<QuestionDTO> apply(AnswerDTO answerDTO) {
        log.info(answerDTO.toString());
        Objects.requireNonNull(answerDTO.getQuestionId(), "Id of the answer is required");
        emailService.sendHTMLMessage(answerDTO.getEmail());

        AnswerDTO newAnswerDto = new AnswerDTO(
                answerDTO.getId(),
                answerDTO.getUserId(),
                answerDTO.getQuestionId(),
                answerDTO.getAnswer(),
                answerDTO.getPosition(),
                answerDTO.getCreated(),
                answerDTO.getUpdated()
        );
        return getUseCase.apply(newAnswerDto.getQuestionId()).flatMap(question ->
                answerRepository.save(mapperUtils.mapperToAnswer().apply(answerDTO))
                        .map(answer -> {
                            question.getAnswers().add(answerDTO);
                            return question;
                        })
        );
    }

}
