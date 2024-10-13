package com.clean.arch.domain.model;

import com.clean.arch.domain.common.Entity;
import com.clean.arch.domain.model.valueobject.FrameId;

public class DicomData extends Entity<FrameId> {
    public DicomData(FrameId frameId) {
        super(frameId);
    }
}
