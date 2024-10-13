package com.clean.arch.application.common.event;

import com.clean.arch.application.common.IApplicationEvent;
import com.clean.arch.domain.model.valueobject.FrameId;

import java.util.List;

public record SeriesDuplicatedEvent(String seriesInstanceId, List<FrameId> duplicatedFrames)
        implements IApplicationEvent {
}
