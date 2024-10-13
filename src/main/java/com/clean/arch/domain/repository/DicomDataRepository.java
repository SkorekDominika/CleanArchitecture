package com.clean.arch.domain.repository;

import com.clean.arch.domain.model.DicomData;
import com.clean.arch.domain.model.valueobject.DicomDataSource;
import com.clean.arch.domain.model.valueobject.FrameId;

import java.util.List;

public interface DicomDataRepository extends CrudRepository<FrameId, DicomData> {

  List<FrameId> getAllIds(DicomDataSource dataSource);

  List<DicomData> getAllBySourceAndStudyInstanceUid(
      DicomDataSource dataSource, String studyInstanceUid);
}
