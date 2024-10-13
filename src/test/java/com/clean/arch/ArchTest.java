package com.clean.arch;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

class ArchTest {
  JavaClasses importedClasses = new ClassFileImporter().importPackages("com.clean.arch");

  @Test
  public void layerDependencyConstraint() {
    Architectures.onionArchitecture()
        .domainModels("..domain.model..")
        .domainServices("..domain.service..")
        .applicationServices("..application..")
        .adapter("Presentation", "..presentation..")
        .adapter("Persistence", "..persistence..")
        .check(importedClasses);
  }
}
