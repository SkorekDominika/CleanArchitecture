package com.gra.recist.domain.repository;

import com.gra.recist.domain.model.DicomData;
import com.gra.recist.domain.model.valueobject.DicomDataSource;
import com.gra.recist.domain.model.valueobject.FrameId;

import java.util.List;

public interface DicomDataRepository extends CrudRepository<FrameId, DicomData> {

    List<FrameId> getAllIds(DicomDataSource dataSource);

    List<DicomData> getAllBySourceAndStudyInstanceUid(DicomDataSource dataSource, String studyInstanceUid);
}
