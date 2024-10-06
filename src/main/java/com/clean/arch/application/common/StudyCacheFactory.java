package com.clean.arch.application.common;

import java.util.function.Function;

public interface StudyCacheFactory {

    StudyCache get(StudySourceIdentifier identifier, Function<StudySourceIdentifier, StudyCache> dataSource);


    record StudySourceIdentifier(String path) {

    }
}
