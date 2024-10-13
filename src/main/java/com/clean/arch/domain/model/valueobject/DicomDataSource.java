package com.clean.arch.domain.model.valueobject;

import com.clean.arch.domain.common.ValueObject;

import java.nio.file.Path;
import java.util.Objects;

public final class DicomDataSource implements ValueObject {
    private final SourceType sourceType;
    private final String locator;

    private DicomDataSource(SourceType sourceType, String locator) {
        this.sourceType = sourceType;
        this.locator = locator;
    }

    public static DicomDataSource createForFileSystem(Path path) {
        return new DicomDataSource(SourceType.FILESYSTEM, path.toString());
    }

    public SourceType sourceType() {
        return sourceType;
    }

    public String locator() {
        return locator;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DicomDataSource) obj;
        return Objects.equals(this.sourceType, that.sourceType)
                && Objects.equals(this.locator, that.locator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceType, locator);
    }

    @Override
    public String toString() {
        return "DicomDataSource[" + "sourceType=" + sourceType + ", " + "locator=" + locator + ']';
    }

    public enum SourceType {
        PACS,
        CD,
        FILESYSTEM;
    }
}
