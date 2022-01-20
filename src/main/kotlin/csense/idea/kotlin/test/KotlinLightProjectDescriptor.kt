// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
// from https://github.com/JetBrains/intellij-community/blob/master/plugins/kotlin/test-framework/test/org/jetbrains/kotlin/idea/test/KotlinLightProjectDescriptor.java
package csense.idea.kotlin.test

import com.intellij.openapi.module.*
import com.intellij.openapi.projectRoots.*
import com.intellij.openapi.roots.*
import com.intellij.testFramework.*


open class KotlinLightProjectDescriptor protected constructor() : LightProjectDescriptor() {

    override fun getModuleTypeId(): String {
        return ModuleTypeId.JAVA_MODULE
    }

    override fun getSdk(): Sdk {
        return IdeaTestUtil.getMockJdk18()
    }

    override fun configureModule(
        module: Module,
        model: ModifiableRootModel,
        contentEntry: ContentEntry
    ) {
        configureModule(module, model)
    }

    open fun configureModule(
        module: Module,
        model: ModifiableRootModel
    ) {

    }

    companion object {
        val INSTANCE = KotlinLightProjectDescriptor()
    }
}