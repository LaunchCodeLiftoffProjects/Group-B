package org.launchcode.customhomepage;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.launchcode.customhomepage");

        noClasses()
            .that()
            .resideInAnyPackage("org.launchcode.customhomepage.service..")
            .or()
            .resideInAnyPackage("org.launchcode.customhomepage.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..org.launchcode.customhomepage.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
