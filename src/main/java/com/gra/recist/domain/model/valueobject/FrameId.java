package com.gra.recist.domain.model.valueobject;

import com.gra.recist.domain.common.ValueObject;

public record FrameId(
        DicomDataSource dataSource, // PACKS or Local File
        String studyInstanceId,
        String seriesInstanceId,
        String sopInstanceId,
        String frameId
) implements ValueObject {

}
