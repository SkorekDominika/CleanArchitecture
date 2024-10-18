package com.clean.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

class ArchTest {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("com.clean.arch");

    @Test
    public void layerDependencyConstraint() {

        Architectures.LayeredArchitecture arch = Architectures.layeredArchitecture()
                .consideringOnlyDependenciesInLayers()
                // Define layers
                .layer("Infrastructure").definedBy("..infrastructure..")
                .layer("Presentation").definedBy("..presentation..")
                .layer("Application").definedBy("..application..")
                .layer("Domain").definedBy("..domain..")
                // Add constraints
                .whereLayer("Domain").mayNotAccessAnyLayer()
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Application", "Presentation", "Infrastructure")
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Presentation", "Infrastructure")
                .whereLayer("Presentation").mayNotBeAccessedByAnyLayer()
                .whereLayer("Infrastructure").mayOnlyBeAccessedByLayers("Presentation");
        arch.check(importedClasses);
    }

    @Test
    public void domainIsCompletelyIndependent() {
        ArchRuleDefinition
                .classes()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .onlyDependOnClassesThat()
                .resideInAPackage("[..domain..|java..]").check(importedClasses);
    }

    @Test
    public void externalLibraries() {
        ArchRuleDefinition
                .noClasses()
                .that()
                .resideInAPackage("com.clean.arch.[application|domain]..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("javafx..").check(importedClasses);

        ArchRuleDefinition
                .noClasses()
                .that()
                .resideInAPackage("com.clean.arch.[application|domain]..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("com.google.inject..").check(importedClasses);
    }
}
