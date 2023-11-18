package com.example.springboot.controller;

import com.example.springboot.constant.Constants;
import com.example.springboot.constant.ErrorMessage;
import com.example.springboot.dto.request.CreateClassroomDTO;
import com.example.springboot.dto.request.CreateMultipleChoiceTestDTO;
import com.example.springboot.exception.NotEnoughQuestionException;
import com.example.springboot.exception.QuestionGroupNotFoundException;
import com.example.springboot.exception.QuestionNotFoundException;
import com.example.springboot.service.MultipleChoiceTestService;
import com.example.springboot.util.CustomBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;

@Validated
@RestController
@RequestMapping("/api/v1/multiple-choice-test")
@Slf4j
@AllArgsConstructor
public class MultipleChoiceTestController {
    private static final String DEFAULT_SEARCH = "";
    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_COLUMN = "id";
    private static final String DEFAULT_SIZE = "12";
    private static final String DEFAULT_SORT_INCREASE = "asc";

    private final MultipleChoiceTestService multipleChoiceTestService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    public ResponseEntity<?> createMultipleChoiceTest(@Valid @RequestBody CreateMultipleChoiceTestDTO DTO){
        try {
            return multipleChoiceTestService.createMultipleChoiceTest(DTO);
        } catch (NotEnoughQuestionException ex) {
            LinkedHashMap<String, String> response = new LinkedHashMap<>();
            response.put(Constants.ERROR_CODE_KEY, ErrorMessage.NOT_ENOUGH_QUESTION.getErrorCode());
            response.put(Constants.MESSAGE_KEY, String.format(ErrorMessage.NOT_ENOUGH_QUESTION.getMessage(), ex.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response);
        } catch (QuestionNotFoundException ex) {
            return CustomBuilder.buildQuestionNotFoundResponseEntity();
        } catch (QuestionGroupNotFoundException ex) {
            return CustomBuilder.buildQuestionGroupNotFoundResponseEntity();
        }
    }
}
