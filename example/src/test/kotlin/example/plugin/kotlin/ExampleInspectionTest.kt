package example.plugin.kotlin

import com.intellij.codeInspection.*
import csense.idea.kotlin.test.*
import example.kotlin.plugin.*
import org.junit.*

class ExampleInspectionTest : KotlinLightCodeInsightFixtureTestCaseJunit4() {
    override fun getTestDataPath(): String = "src/test/testData"

    override fun setupInspections(): List<InspectionProfileEntry> {
        return listOf(NoOpInspection())
    }

    @Test
    fun testInspector() {
        //should throw
        myFixture.testHighlighting("ExampleInspector.kt")
    }

}