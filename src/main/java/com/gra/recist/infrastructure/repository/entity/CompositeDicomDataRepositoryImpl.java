package com.gra.recist.infrastructure.repository.entity;

import com.gra.recist.domain.model.DicomData;
import com.gra.recist.domain.model.valueobject.DicomDataSource;
import com.gra.recist.domain.model.valueobject.DicomDataSource.SourceType;
import com.gra.recist.domain.model.valueobject.FrameId;
import com.gra.recist.domain.repository.DicomDataRepository;

import java.util.List;
import java.util.Map;

public class CompositeDicomDataRepositoryImpl implements DicomDataRepository {

    Map<SourceType, DicomDataRepository> repositoryByDataSource;

    @Override
    public DicomData create(DicomData data) {
        SourceType key = data.getId().dataSource().sourceType();
        DicomDataRepository dataRepository = repositoryByDataSource.get(key);
        return dataRepository.create(data);
    }

    @Override
    public DicomData read(FrameId frameId) {
        SourceType key = frameId.dataSource().sourceType();
        DicomDataRepository dataRepository = repositoryByDataSource.get(key);
        return dataRepository.read(frameId);
    }

    @Override
    public DicomData update(DicomData data) {
        SourceType key = data.getId().dataSource().sourceType();
        DicomDataRepository dataRepository = repositoryByDataSource.get(key);
        return dataRepository.update(data);
    }

    @Override
    public DicomData delete(FrameId frameId) {
        SourceType key = frameId.dataSource().sourceType();
        DicomDataRepository dataRepository = repositoryByDataSource.get(key);
        return dataRepository.delete(frameId);
    }

    @Override
    public List<FrameId> getAllIds(DicomDataSource dataSource) {
        SourceType key = dataSource.sourceType();
        DicomDataRepository dataRepository = repositoryByDataSource.get(key);
        return dataRepository.getAllIds(dataSource);
    }

    @Override
    public List<DicomData> getAllBySourceAndStudyInstanceUid(DicomDataSource dataSource, String studyInstanceUid) {
        SourceType key = dataSource.sourceType();
        DicomDataRepository dataRepository = repositoryByDataSource.get(key);
        return dataRepository.getAllBySourceAndStudyInstanceUid(dataSource, studyInstanceUid);
    }
}
