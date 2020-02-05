package com.test.test_app.mvc.handlers;

import com.test.test_app.service.MainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Api(tags = "main-controller")
@RestController
@Validated
public class MainController {

    private final MainService service;

    @Autowired
    MainController(MainService service) {
        this.service = service;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/updateState")
    void updateState(@ApiParam(value = "Int value in range [0-255] - increases count of producers by provided value", example = "10")
                     @RequestParam("producerCntIncBy")
                     @Min(0) @Max(255) Integer producerCntIncBy,
                     @ApiParam(value = "Int value in range [0-255] - increases count of consumers by provided value", example = "10")
                     @RequestParam("consumerCntIncBy")
                     @Min(0) @Max(255) Integer consumerCntIncBy) {

        service.setState(producerCntIncBy, consumerCntIncBy);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/updateCounter")
    void updateState(@ApiParam(value = "Int value in range [0-100] - set the value of counter", example = "50")
                     @RequestParam("cntValue")
                     @Min(0) @Max(100) Integer cntValue) {

        service.setCounter(cntValue);
    }

}