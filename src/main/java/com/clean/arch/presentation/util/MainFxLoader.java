package com.clean.arch.presentation.util;

import com.clean.arch.infrastructure.di.scope.window.WindowScope;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;

import java.io.IOException;

public class MainFxLoader {

    @Inject
    private WindowScope windowScope;

    @Inject
    private Injector injector;

    public <T extends Parent, CONTROLLER> ViewControllerReference<T, CONTROLLER> load(
            Parent parent, Class<CONTROLLER> clazz) throws IOException {
        return load(parent.getScene().getWindow(), clazz);
    }

    public <T extends Parent, CONTROLLER> ViewControllerReference<T, CONTROLLER> load(
            Window window, Class<CONTROLLER> clazz) throws IOException {
        try {
            windowScope.enter(window);
            FXMLLoader fxmlLoader = new FXMLLoader(clazz.getResource(clazz.getSimpleName() + ".fxml"));
            fxmlLoader.setControllerFactory(injector::getInstance);
            T fxmlObject = fxmlLoader.load();
            CONTROLLER controller = fxmlLoader.getController();
            fxmlObject.setUserData(controller); // Force JavaFx to keep the strong reference to the controller.
            return new ViewControllerReference<>(fxmlObject, controller);
        } finally {
            windowScope.exit();
        }
    }

    public record ViewControllerReference<T extends Parent, CONTROLLER>(
            T viewNode, CONTROLLER controller) {
    }
}
