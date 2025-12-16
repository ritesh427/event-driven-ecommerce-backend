package com.ritesh.sampleservice.controller;

import com.ritesh.sampleservice.dto.BaseResponse;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);


    @GetMapping("/error")
    public String throwIllegalArgument() {
        throw new IllegalArgumentException("Invalid request");
    }

    @GetMapping("/validate")
    public BaseResponse<String> validate(
            @RequestParam @NotBlank(message = "name must not be blank") String name) {
        return BaseResponse.success("Hello " + name, MDC.get("correlationId"));
    }

    @GetMapping("/ok")
    public BaseResponse<String> ok() {
        log.info("OK endpoint called");
        return BaseResponse.success("Everything is fine", MDC.get("correlationId"));
    }

}
