package com.gra.recist;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.gra.recist.application.service.RecistAnalysisService;
import com.gra.recist.infrastructure.di.CommonModule;
import com.gra.recist.presentation.analyser.RecistAnalyser;
import com.gra.recist.presentation.util.MainFxLoader;
import com.gra.recist.presentation.util.MainFxLoader.ViewControllerReference;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class StartApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        Injector injector = Guice.createInjector(new CommonModule());
        MainFxLoader mainFxLoader = injector.getInstance(MainFxLoader.class);
        ViewControllerReference<Parent, RecistAnalyser> viewControllerReference = mainFxLoader.load(RecistAnalyser.class);
        UUID recistId = injector.getInstance(RecistAnalysisService.class).createRecistAnalysis().get();
        viewControllerReference.controller().setRecistAnalysis(recistId);

        Scene scene = new Scene(viewControllerReference.viewNode(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}