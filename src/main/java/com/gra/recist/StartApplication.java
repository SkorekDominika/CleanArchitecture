package com.gra.recist;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.gra.recist.infrastructure.di.AppServicesModule;
import com.gra.recist.infrastructure.di.CommonModule;
import com.gra.recist.infrastructure.di.PersistenceModule;
import com.gra.recist.infrastructure.di.ScopeModule;
import com.gra.recist.presentation.studyopener.StudyOpener;
import com.gra.recist.presentation.util.MainFxLoader;
import com.gra.recist.presentation.util.MainFxLoader.ViewControllerReference;
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