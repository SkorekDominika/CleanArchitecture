package com.clean.arch.domain.model;

import com.clean.arch.domain.model.valueobject.FrameId;
import com.clean.arch.domain.common.Entity;

public class DicomData extends Entity<FrameId> {
    public DicomData(FrameId frameId) {
        super(frameId);
    }
}
