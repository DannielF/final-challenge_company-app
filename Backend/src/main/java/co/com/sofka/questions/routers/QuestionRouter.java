package co.com.sofka.questions.routers;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.usecases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class QuestionRouter {

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getAll", summary = "Get all questions",
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionDTO.class))
            })}
    ))
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(), QuestionDTO.class))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "getOwnerAll", summary = "Get all questions by userId",
            responses = {@ApiResponse(responseCode = "200", description = "Successful", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionDTO.class))
            })},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "userId",
                    description = "User Id",
                    required = true)}
    ))
    public RouterFunction<ServerResponse> getOwnerAll(OwnerListUseCase ownerListUseCase) {
        return route(
                GET("/getOwnerAll/{userId}"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(
                                ownerListUseCase.apply(request.pathVariable("userId")),
                                QuestionDTO.class
                        ))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "create", summary = "Create a question",
            responses = {@ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionDTO.class))
            })},
            requestBody = @RequestBody(required = true, description = "Insert a QuestionDTO",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDTO.class))
                    })
    ))
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<QuestionDTO, Mono<ServerResponse>> executor = questionDTO -> createUseCase.apply(questionDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(result));

        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class).flatMap(executor)
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "Get", summary = "Get a question",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id",
                            description = "Question Id",
                            required = true)},
            responses = {@ApiResponse(responseCode = "200", description = "Successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDTO.class))
                    })}
    ))
    public RouterFunction<ServerResponse> get(GetUseCase getUseCase) {
        return route(
                GET("/get/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getUseCase.apply(
                                        request.pathVariable("id")),
                                QuestionDTO.class
                        ))
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "addAnswer", summary = "Add an answer",
            requestBody = @RequestBody(required = true, description = "Insert an AnswerDTO",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnswerDTO.class))
                    }),
            responses = {@ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerDTO.class))
            })}
    ))
    public RouterFunction<ServerResponse> addAnswer(AddAnswerUseCase addAnswerUseCase) {
        return route(POST("/add").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AnswerDTO.class)
                        .flatMap(addAnswerDTO -> addAnswerUseCase.apply(addAnswerDTO)
                                .flatMap(result -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                        )
        );
    }

    @Bean
    @RouterOperation(operation = @Operation(operationId = "update", summary = "Update a question",

            requestBody = @RequestBody(required = true, description = "Insert a QuestionDto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDTO.class))
                    }),
            responses = {@ApiResponse(responseCode = "200", description = "Successfully updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionDTO.class))
            })}
    ))
    public RouterFunction<ServerResponse> update(UpdateUseCase updateUseCase) {
        return route(
                PUT("/updateQuestion").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(QuestionDTO.class)
                        .flatMap(questionUpdate -> updateUseCase.apply(questionUpdate)
                                .flatMap(result -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result)
                                )
                        )
        );
    }


    @Bean
    @RouterOperation(operation = @Operation(operationId = "deleteQuestion", summary = "Remove a question by its id",
            responses = {@ApiResponse(responseCode = "200", description = "Question removed", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionDTO.class))
            })},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id",
                    description = "question id",
                    required = true)}
    ))
    public RouterFunction<ServerResponse> deleteQuestion(DeleteUseCase deleteUseCase) {
        return route(
                DELETE("/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.accepted()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(deleteUseCase.apply(request.pathVariable("id")), Void.class))
        );
    }
}


