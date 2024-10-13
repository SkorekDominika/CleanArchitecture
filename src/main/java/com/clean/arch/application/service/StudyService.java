package com.clean.arch.application.service;

import com.clean.arch.application.common.IApplicationEvent;
import com.clean.arch.application.common.ObservableService;
import com.clean.arch.application.common.event.SeriesDuplicatedEvent;
import com.clean.arch.domain.model.DicomData;
import com.clean.arch.domain.model.valueobject.DicomDataSource;
import com.clean.arch.domain.model.valueobject.FrameId;
import com.clean.arch.domain.repository.DicomDataRepository;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public class StudyService extends ObservableService<IApplicationEvent> {

    private final DicomDataRepository dicomDataRepository;
    private final ExecutorService backendExecutors;
    private final AsyncLoadingCache<FrameId, DicomData> cache;

    public StudyService(DicomDataRepository dicomDataRepository, ExecutorService backendExecutors) {
        this.dicomDataRepository = dicomDataRepository;
        this.backendExecutors = backendExecutors;
        this.cache = Caffeine.newBuilder().executor(backendExecutors).buildAsync(dicomDataRepository::read);
    }

    public CompletableFuture<DicomData> loadStudy(FrameId frameId) {
        return cache.get(frameId);
    }

    public Map<FrameId, CompletableFuture<DicomData>> loadStudy(Collection<FrameId> frameIds) {
        return frameIds.stream().collect(Collectors.toMap(Function.identity(), cache::get));
    }

    public List<FrameId> loadAllHeaders(DicomDataSource dataSource) {
        return dicomDataRepository.getAllIds(dataSource);
    }

    public void duplicateSeries(Set<FrameId> frameIds) {
        backendExecutors.submit(
                () -> {
                    List<FrameId> duplicatedFrames =
                            frameIds.stream()
                                    .map(frameId -> Pair.of(frameId, cache.get(frameId)))
                                    .map(
                                            pair -> {
                                                String newStudyInstanceId = pair.getKey().studyInstanceId() + ".1";
                                                String newSeriesInstanceId = pair.getKey().seriesInstanceId() + ".1";
                                                String newSopInstanceId = pair.getKey().sopInstanceId() + ".1";
                                                FrameId duplicatedFrameId =
                                                        new FrameId(
                                                                pair.getKey().dataSource(),
                                                                newStudyInstanceId,
                                                                newSeriesInstanceId,
                                                                newSopInstanceId,
                                                                pair.getKey().frameId());

                                                cache.put(
                                                        duplicatedFrameId,
                                                        pair.getValue()
                                                                .thenApply(oldDicom -> new DicomData(duplicatedFrameId, oldDicom.getImage())));
                                                return duplicatedFrameId;
                                            })
                                    .toList();
                    String seriesInstanceId = duplicatedFrames.stream().findFirst().get().seriesInstanceId();
                    notify(new SeriesDuplicatedEvent(seriesInstanceId, duplicatedFrames));
                });
    }
}
