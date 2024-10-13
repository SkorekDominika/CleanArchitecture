package com.clean.arch;

import com.clean.arch.infrastructure.di.scope.AppServicesModule;
import com.clean.arch.infrastructure.di.scope.CommonModule;
import com.clean.arch.infrastructure.di.scope.PersistenceModule;
import com.clean.arch.infrastructure.di.scope.ScopeModule;
import com.clean.arch.presentation.studyopener.StudyOpener;
import com.clean.arch.presentation.util.MainFxLoader;
import com.clean.arch.presentation.util.MainFxLoader.ViewControllerReference;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Injector injector = Guice.createInjector(new CommonModule(), new ScopeModule(), new AppServicesModule(), new PersistenceModule());
        MainFxLoader mainFxLoader = injector.getInstance(MainFxLoader.class);
        ViewControllerReference<Parent, StudyOpener> viewControllerReference = mainFxLoader.load(stage, StudyOpener.class);

        Scene scene = new Scene(viewControllerReference.viewNode(), 320, 240);
        stage.setTitle("Exhibeon Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}