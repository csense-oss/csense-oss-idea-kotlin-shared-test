// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

// from https://github.com/JetBrains/intellij-community/tree/master/plugins/kotlin/test-framework/test/org/jetbrains/kotlin/idea/test

package csense.idea.kotlin.test

import com.intellij.openapi.application.*
import com.intellij.openapi.roots.*
import com.intellij.openapi.roots.impl.libraries.*
import com.intellij.openapi.roots.libraries.*
import com.intellij.openapi.roots.ui.configuration.libraryEditor.*
import com.intellij.openapi.module.Module as Module

object ConfigLibraryUtil {
    fun addLibrary(
        module: Module,
        name: String,
        kind: PersistentLibraryKind<*>? = null,
        init: Library.ModifiableModel.() -> Unit
    ) {
        runWriteAction {
            ModuleRootManager.getInstance(module).modifiableModel.apply {
                addLibrary(this, name, kind, init)
                commit()
            }
        }
    }

    fun addLibrary(
        rootModel: ModifiableRootModel,
        name: String, kind: PersistentLibraryKind<*>? = null,
        init: Library.ModifiableModel.() -> Unit
    ) {
        rootModel.moduleLibraryTable.modifiableModel.apply {
            val library = createLibrary(name, kind)
            library.modifiableModel.apply {
                init()
                commit()
            }

            commit()
        }
    }

    fun addLibrary(
        editor: NewLibraryEditor,
        model: ModifiableRootModel,
        kind: PersistentLibraryKind<*>? = null
    ): Library {
        val libraryTableModifiableModel = model.moduleLibraryTable.modifiableModel
        val library = libraryTableModifiableModel.createLibrary(editor.name, kind)

        val libModel = library.modifiableModel
        try {
            editor.applyTo(libModel as LibraryEx.ModifiableModelEx)
        } finally {
            libModel.commit()
            libraryTableModifiableModel.commit()
        }

        return library
    }


}