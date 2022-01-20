// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

//from https://github.com/JetBrains/intellij-community/tree/master/plugins/kotlin/test-framework/test/org/jetbrains/kotlin/idea/test

package csense.idea.kotlin.test

import com.intellij.openapi.module.Module
import com.intellij.openapi.projectRoots.Sdk
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.libraries.*
import com.intellij.openapi.vfs.*
import com.intellij.testFramework.IdeaTestUtil
import csense.idea.kotlin.test.*
import java.io.File

open class KotlinJdkAndLibraryProjectDescriptor(
    val libraryFiles: List<File>,
    val librarySourceFiles: List<File> = emptyList()
) :
    KotlinLightProjectDescriptor() {

    constructor(libraryFile: File) : this(listOf(libraryFile))

    init {
        for (libraryFile in libraryFiles + librarySourceFiles) {
            assert(libraryFile.exists()) { "Library file doesn't exist: " + libraryFile.absolutePath }
        }
    }

    override fun getSdk(): Sdk = IdeaTestUtil.getMockJdk18()

    override fun configureModule(module: Module, model: ModifiableRootModel) {
        ConfigLibraryUtil.addLibrary(model, LIBRARY_NAME) {
            for (libraryFile in libraryFiles) {
                addRoot(libraryFile, OrderRootType.CLASSES)
            }
            for (librarySrcFile in librarySourceFiles) {
                addRoot(librarySrcFile, OrderRootType.SOURCES)
            }
        }
    }

    companion object {
        const val LIBRARY_NAME = "myLibrary"
    }
}

fun Library.ModifiableModel.addRoot(file: File, kind: OrderRootType) {
    addRoot(VfsUtil.getUrlForLibraryRoot(file), kind)
}
