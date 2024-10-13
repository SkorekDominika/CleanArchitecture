package com.clean.arch.domain.model.valueobject;

import com.clean.arch.domain.common.ValueObject;

public record FrameId(
        DicomDataSource dataSource, // PACKS or Local File
        String studyInstanceId,
        String seriesInstanceId,
        String sopInstanceId,
        String frameId)
        implements ValueObject {
}
