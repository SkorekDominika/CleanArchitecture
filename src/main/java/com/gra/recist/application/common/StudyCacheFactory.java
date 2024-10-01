package com.gra.recist.application.common;

import java.util.function.Function;
import java.util.function.Supplier;

public interface StudyCacheFactory {

    StudyCache get(StudySourceIdentifier identifier, Function<StudySourceIdentifier, StudyCache> dataSource);


    record StudySourceIdentifier(String path) {

    }
}
