package com.clean.arch.presentation.studypresenter.command;

import com.clean.arch.domain.model.valueobject.FrameId;

import java.util.Set;

public record DuplicateSeries(Set<FrameId> frameIds) {}
