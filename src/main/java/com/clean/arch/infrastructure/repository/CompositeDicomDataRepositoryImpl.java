package com.clean.arch.infrastructure.repository;

import com.clean.arch.domain.model.DicomData;
import com.clean.arch.domain.model.valueobject.DicomDataSource;
import com.clean.arch.domain.model.valueobject.DicomDataSource.SourceType;
import com.clean.arch.domain.model.valueobject.FrameId;
import com.clean.arch.domain.repository.DicomDataRepository;

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
    public List<DicomData> getAllBySourceAndStudyInstanceUid(
            DicomDataSource dataSource, String studyInstanceUid) {
        SourceType key = dataSource.sourceType();
        DicomDataRepository dataRepository = repositoryByDataSource.get(key);
        return dataRepository.getAllBySourceAndStudyInstanceUid(dataSource, studyInstanceUid);
    }
}
