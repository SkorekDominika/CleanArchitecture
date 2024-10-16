package com.clean.arch.infrastructure.di.scope;

import com.clean.arch.infrastructure.di.scope.hp.HangingProtocolScope;
import com.clean.arch.infrastructure.di.scope.hp.HangingProtocolScoped;
import com.clean.arch.infrastructure.di.scope.window.WindowScope;
import com.clean.arch.infrastructure.di.scope.window.WindowScoped;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ScopeModule extends AbstractModule {

    private final HangingProtocolScope hpScope = new HangingProtocolScope();
    private final WindowScope windowScope = new WindowScope();

    @Override
    protected void configure() {
        bindScope(HangingProtocolScoped.class, hpScope);
        bindScope(WindowScoped.class, windowScope);
    }

    @Provides
    HangingProtocolScope provideHangingProtocolScope() {
        return hpScope;
    }

    @Provides
    WindowScope provideWindowScope() {
        return windowScope;
    }
}
