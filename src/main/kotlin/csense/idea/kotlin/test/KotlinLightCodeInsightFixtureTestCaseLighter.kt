// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
//based on https://github.com/JetBrains/intellij-community/blob/master/plugins/kotlin/test-framework/test/org/jetbrains/kotlin/idea/test/KotlinLightCodeInsightFixtureTestCase.kt
package csense.idea.kotlin.test

import com.intellij.codeInspection.*
import com.intellij.openapi.application.*
import com.intellij.openapi.module.*
import com.intellij.openapi.project.*
import com.intellij.openapi.roots.*
import com.intellij.openapi.vfs.newvfs.impl.*
import com.intellij.pom.java.*
import com.intellij.testFramework.*
import com.intellij.testFramework.fixtures.*
import org.jetbrains.kotlin.idea.caches.project.*
import java.io.*


abstract class KotlinLightCodeInsightFixtureTestCaseLighter : BasePlatformTestCase() {

    abstract override fun getTestDataPath(): String

    open fun shouldAllowASTAccessForAllFiles(): Boolean = false


    override fun setUp() {
        super.setUp()
        VfsRootAccess.allowRootAccess(myFixture.testRootDisposable, KotlinRoot.DIR.path)
        if (shouldAllowASTAccessForAllFiles()) {
            myFixture.allowTreeAccessForAllFiles()
        }
        setupInspections()
        invalidateLibraryCache(project)
    }

    /**
     * Hookpoint to setup inspections for the given test(s)
     * @return List<InspectionProfileEntry>
     */
    open fun setupInspections(): List<InspectionProfileEntry> {
        return emptyList()
    }

    //Change this in order to change the sdk / jdk.
    override fun getProjectDescriptor(): LightProjectDescriptor {
        return KotlinWithJdkAndRuntimeLightProjectDescriptor.INSTANCE_FULL_JDK
    }
}

object KotlinRoot {
    @JvmField
    val REPO: File = File(PathManager.getHomePath())

    @JvmField
    val DIR: File = REPO.resolve("community/plugins/kotlin").takeIf { it.exists() }
        ?: File(PathManager.getCommunityHomePath()).resolve("plugins/kotlin")
}


fun invalidateLibraryCache(project: Project) {
    LibraryModificationTracker.getInstance(project).incModificationCount()
}