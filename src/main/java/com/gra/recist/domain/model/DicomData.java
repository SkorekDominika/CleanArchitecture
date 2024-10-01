package com.gra.recist.domain.model;

import com.gra.recist.domain.model.valueobject.FrameId;
import com.gra.recist.domain.common.Entity;

public class DicomData extends Entity<FrameId> {
    public DicomData(FrameId frameId) {
        super(frameId);
    }
}
