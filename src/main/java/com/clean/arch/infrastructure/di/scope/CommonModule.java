package com.clean.arch.infrastructure.di.scope;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.clean.arch.infrastructure.di.scope.window.WindowScoped;
import net.engio.mbassy.bus.MBassador;

public class CommonModule extends AbstractModule {

    @Provides
    @WindowScoped
    @Named("windowMQ")
    MBassador<Object> mBassador() {
        return new MBassador<>();
    }

}
