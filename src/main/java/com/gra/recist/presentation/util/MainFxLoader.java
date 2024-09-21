package com.gra.recist.presentation.util;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class MainFxLoader {

    @Inject
    private Injector injector;

    public <T extends Parent, CONTROLLER> ViewControllerReference<T, CONTROLLER> load(Class<CONTROLLER> clazz) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(clazz.getResource(clazz.getSimpleName() + ".fxml"));
        fxmlLoader.setControllerFactory(injector::getInstance);
        return new ViewControllerReference<>(fxmlLoader.load(), fxmlLoader.getController());
    }

    public record ViewControllerReference<T extends Parent, CONTROLLER>(T viewNode, CONTROLLER controller) {
    }
}
