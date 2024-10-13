package com.clean.arch.domain.model;

import com.clean.arch.domain.common.Entity;
import com.clean.arch.domain.model.valueobject.FrameId;

public class DicomData extends Entity<FrameId> {
  private final byte[] image;

  public DicomData(FrameId frameId, byte[] image) {
    super(frameId);
    this.image = image;
  }

  public byte[] getImage() {
    return image;
  }
}
