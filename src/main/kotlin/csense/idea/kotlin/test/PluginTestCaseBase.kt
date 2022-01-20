// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package csense.idea.kotlin.test


import com.intellij.openapi.*
import com.intellij.openapi.application.*
import com.intellij.openapi.projectRoots.*
import com.intellij.testFramework.*
import org.jetbrains.kotlin.idea.util.*
import java.io.*
import kotlin.jvm.functions.*


object PluginTestCaseBase {
    private fun getSdk(sdkHome: String, name: String): Sdk {
        val table = getProjectJdkTableSafe()
        val existing = table.findJdk(name)
        return existing ?: JavaSdk.getInstance().createJdk(name, sdkHome, true)
    }

    fun fullJdk(): Sdk {
        val javaHome = System.getProperty("java.home")
        assert(File(javaHome).isDirectory)
        return getSdk(javaHome, "Full JDK")
    }

    fun addJdk(disposable: Disposable, getJdk: kotlin.Function0<Sdk>): Sdk {
        val jdk = getJdk.invoke()
        val allJdks = getProjectJdkTableSafe().allJdks
        for (existingJdk in allJdks) {
            if (existingJdk === jdk) {
                return existingJdk
            }
        }
        ApplicationManager.getApplication().runWriteAction {
            getProjectJdkTableSafe().addJdk(
                jdk,
                disposable
            )
        }
        return jdk
    }

}