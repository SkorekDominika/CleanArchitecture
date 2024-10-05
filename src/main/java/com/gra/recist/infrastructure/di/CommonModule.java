package com.gra.recist.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.gra.recist.infrastructure.di.scope.window.WindowScoped;
import net.engio.mbassy.bus.MBassador;

public class CommonModule extends AbstractModule {

    @Provides
    @WindowScoped
    @Named("windowMQ")
    MBassador<Object> mBassador() {
        return new MBassador<>();
    }

}
