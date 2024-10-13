package com.clean.arch.presentation.studypresenter.event;

import com.clean.arch.domain.model.DicomData;

import java.util.concurrent.CompletableFuture;

public record FrameProvided(CompletableFuture<DicomData> dicomDataCompletableFuture) {
}
