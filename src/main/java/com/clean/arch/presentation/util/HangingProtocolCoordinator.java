package com.clean.arch.presentation.util;

import com.clean.arch.infrastructure.di.scope.hp.HangingProtocolScope;
import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class is responsible for creating and managing windows that belong to the same hanging protocol.
 * It contains the logic for creating the appropriate number of windows based on the requirements of the protocol.
 * The class ensures that the windows are properly initialized and managed throughout their lifecycle.
 */
public class HangingProtocolCoordinator {

    private final int hpInstances = 2;

    @Inject
    private HangingProtocolScope hpScope;

    @Inject
    private MainFxLoader mainFxLoader;

    public <CONTROLLER> List<Stage> prepareViews(
            Class<CONTROLLER> clazz, Consumer<CONTROLLER> postConstruct) throws IOException {
        try {
            hpScope.enter();
            return IntStream.range(0, hpInstances)
                    .mapToObj(
                            idx -> {
                                try {
                                    Stage stage = new Stage();
                                    MainFxLoader.ViewControllerReference<Parent, CONTROLLER> presenterReference =
                                            mainFxLoader.load(stage, clazz);
                                    stage.setScene(new Scene(presenterReference.viewNode(), 800, 500));
                                    postConstruct.accept(presenterReference.controller());
                                    return stage;
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                    .collect(Collectors.toList());
        } finally {
            hpScope.exit();
        }
    }
}
