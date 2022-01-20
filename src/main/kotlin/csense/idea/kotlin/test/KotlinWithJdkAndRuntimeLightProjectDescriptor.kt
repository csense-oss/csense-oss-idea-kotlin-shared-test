// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
//from //from https://github.com/JetBrains/intellij-community/tree/master/plugins/kotlin/test-framework/test/org/jetbrains/kotlin/idea/test
package csense.idea.kotlin.test

import com.intellij.openapi.module.*
import com.intellij.openapi.projectRoots.*
import com.intellij.openapi.roots.*
import com.intellij.pom.java.*
import org.jetbrains.kotlin.idea.artifacts.*
import java.io.*
import java.util.*


open class KotlinWithJdkAndRuntimeLightProjectDescriptor : KotlinJdkAndLibraryProjectDescriptor {
    protected constructor() : super(
        listOf<File>(KotlinArtifacts.instance.kotlinStdlib),
        listOf<File>(KotlinArtifacts.instance.kotlinStdlibSources)
    ) {
    }

    constructor(
        libraryFiles: List<File>,
        librarySourceFiles: List<File>
    ) : super(libraryFiles, librarySourceFiles) {
    }

    companion object {
        val INSTANCE = KotlinWithJdkAndRuntimeLightProjectDescriptor(
            Arrays.asList(KotlinArtifacts.instance.kotlinStdlib),
            listOf<File>(KotlinArtifacts.instance.kotlinStdlibSources)
        )
        val INSTANCE_NO_SOURCES = KotlinWithJdkAndRuntimeLightProjectDescriptor(
            INSTANCE.libraryFiles, emptyList<File>()
        )

        fun getInstance(level: LanguageLevel?): KotlinWithJdkAndRuntimeLightProjectDescriptor {
            return object : KotlinWithJdkAndRuntimeLightProjectDescriptor() {
                override fun configureModule(
                    module: Module, model: ModifiableRootModel
                ) {
                    super.configureModule(module, model)
                    model.getModuleExtension(LanguageLevelModuleExtension::class.java).languageLevel =
                        level
                }
            }
        }

        val INSTANCE_WITH_KOTLIN_TEST = KotlinWithJdkAndRuntimeLightProjectDescriptor(
            Arrays.asList(
                KotlinArtifacts.instance.kotlinStdlib,
                KotlinArtifacts.instance.kotlinTest
            ), listOf<File>(KotlinArtifacts.instance.kotlinStdlibSources)
        )
        val INSTANCE_WITH_SCRIPT_RUNTIME = KotlinWithJdkAndRuntimeLightProjectDescriptor(
            Arrays.asList(
                KotlinArtifacts.instance.kotlinStdlib,
                KotlinArtifacts.instance.kotlinScriptRuntime
            ), listOf<File>(KotlinArtifacts.instance.kotlinStdlibSources)
        )
        val INSTANCE_WITH_REFLECT = KotlinWithJdkAndRuntimeLightProjectDescriptor(
            Arrays.asList(
                KotlinArtifacts.instance.kotlinStdlib,
                KotlinArtifacts.instance.kotlinReflect
            ), listOf<File>(KotlinArtifacts.instance.kotlinStdlibSources)
        )
        val INSTANCE_FULL_JDK: KotlinWithJdkAndRuntimeLightProjectDescriptor =
            object : KotlinWithJdkAndRuntimeLightProjectDescriptor() {
                override fun getSdk(): Sdk {
                    return PluginTestCaseBase.fullJdk()
                }
            }
    }
}