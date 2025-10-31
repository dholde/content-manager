package com.dehold.contentmanager.validation.web;

import com.dehold.contentmanager.validation.service.ForbiddenWordsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forbidden-words")
public class ForbiddenWordsController {

    private final ForbiddenWordsService forbiddenWordsService;

    public ForbiddenWordsController(ForbiddenWordsService forbiddenWordsService) {
        this.forbiddenWordsService = forbiddenWordsService;
    }



}
