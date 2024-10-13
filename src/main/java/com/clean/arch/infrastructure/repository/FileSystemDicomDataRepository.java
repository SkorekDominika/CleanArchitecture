package com.clean.arch.infrastructure.repository;

import com.clean.arch.domain.model.DicomData;
import com.clean.arch.domain.model.valueobject.DicomDataSource;
import com.clean.arch.domain.model.valueobject.FrameId;
import com.clean.arch.domain.repository.DicomDataRepository;
import org.apache.commons.lang3.NotImplementedException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Stream;

public class FileSystemDicomDataRepository implements DicomDataRepository {

    private static final Random random = new Random();

    @Override
    public DicomData create(DicomData data) {
        throw new NotImplementedException();
    }

    @Override
    public DicomData read(FrameId frameId) {
        String path = Paths.get(frameId.dataSource().locator(), frameId.seriesInstanceId(), frameId.sopInstanceId() + ".dcm").toString();
        Properties properties = new Properties();
        byte[] image;
        try {
            properties.load(new FileInputStream(path));
            image = Base64.getDecoder().decode(properties.getProperty("image"));

            // fake loading time
            Thread.sleep(random.nextInt(40) * 100);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new DicomData(frameId, image);
    }

    @Override
    public DicomData update(DicomData data) {
        throw new NotImplementedException();
    }

    @Override
    public DicomData delete(FrameId frameId) {
        throw new NotImplementedException();
    }

    @Override
    public List<FrameId> getAllIds(DicomDataSource dataSource) {
        Path rootPath = Paths.get(dataSource.locator());
        try (Stream<Path> walk = Files.walk(rootPath)) {
            return walk.map(Path::toFile)
                    .filter(File::isFile)
                    .filter(file -> file.getName().matches(".*\\.(dcm)|(DCM)"))
                    .map(
                            file -> {
                                try {
                                    Properties properties = new Properties();
                                    properties.load(new FileInputStream(file));
                                    return properties;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                    .map(
                            properties ->
                                    new FrameId(
                                            dataSource,
                                            properties.getProperty("studyInstanceId"),
                                            properties.getProperty("seriesInstanceId"),
                                            properties.getProperty("sopInstanceId"),
                                            properties.getProperty("frameId")))
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DicomData> getAllBySourceAndStudyInstanceUid(
            DicomDataSource dataSource, String studyInstanceUid) {
        throw new NotImplementedException();
    }
}
