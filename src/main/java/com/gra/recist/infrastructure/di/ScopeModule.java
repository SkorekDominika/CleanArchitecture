package com.gra.recist.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.gra.recist.infrastructure.di.scope.hp.HangingProtocolScope;
import com.gra.recist.infrastructure.di.scope.hp.HangingProtocolScoped;

public class ScopeModule extends AbstractModule {

    private final HangingProtocolScope hpScope = new HangingProtocolScope();

    @Override
    protected void configure() {
        bindScope(HangingProtocolScoped.class, hpScope);
    }

    @Provides
    @Named("hangingProtocolScope")
    HangingProtocolScope provideHangingProtocolScope() {
        return hpScope;
    }
}
