package example.plugin.kotlin

import csense.idea.kotlin.test.*
import org.junit.*

class ExampleTest : KotlinLightCodeInsightFixtureTestCaseJunit4() {
    override fun getTestDataPath(): String = "src/test/testData"

    @Test
    fun testExample() {
        //should not throw; , and not contain a "reference not found" error.. if so then the std lib is not loaded correctly
        myFixture.testHighlighting("Example.kt")
    }

}