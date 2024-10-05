package com.gra.recist.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.gra.recist.infrastructure.di.scope.hp.HangingProtocolScope;
import com.gra.recist.infrastructure.di.scope.hp.HangingProtocolScoped;
import com.gra.recist.infrastructure.di.scope.window.WindowScope;
import com.gra.recist.infrastructure.di.scope.window.WindowScoped;

public class ScopeModule extends AbstractModule {

    private final HangingProtocolScope hpScope = new HangingProtocolScope();
    private final WindowScope windowScope = new WindowScope();

    @Override
    protected void configure() {
        bindScope(HangingProtocolScoped.class, hpScope);
        bindScope(WindowScoped.class, windowScope);
    }

    @Provides
    @Named("hangingProtocolScope")
    HangingProtocolScope provideHangingProtocolScope() {
        return hpScope;
    }

    @Provides
    @Named("windowScope")
    WindowScope provideWindowScope() {
        return windowScope;
    }
}
