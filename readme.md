# Introduction

This library is a "workaround" the lack of real documentation & library for testing Intellij IDEA plugins in kotlin &
with the kotlin stdLib presented.

There is an example under the example folder
## Installation

### Add the csense maven repo

groovy dsl

```groovy
repositories {
    maven {
        url 'https://pkgs.dev.azure.com/csense-oss/csense-oss/_packaging/csense-oss/maven/v1'
        name 'csense-oss'
    }
}
```

kotlin gradle dsl

```kotlin
repositories {
    maven {
        setUrl("https://pkgs.dev.azure.com/csense-oss/csense-oss/_packaging/csense-oss/maven/v1")
        name = "Csense oss"
    }
}
```

### Add the library

groovy dsl

```groovy
dependencies {
    testImplementation "csense.idea.test:csense-idea-test:0.1.0"
}
```

kotlin gradle dsl

```kotlin
dependencies {
    testImplementation("csense.idea.test:csense-idea-test:0.1.0")
}
```

## Getting started / steps explained

There are a few things to set up before any tests of sorts can work.

(the Example folder contains a simple example which the following steps will create)

1) the structure of tests can / are only tested as follows:
   adding this to gradle

 ```kotlin
      sourceSets {
    test {
        resources {
            srcDir("testData")
        }
    }
}
```

Then adding directly under the test folder a folder called "testData" (of cause you can customize this)

2) Add test fixtures inside here. as an "getting started example" you can use this in a "Example.kt"

```kotlin
fun main(args: Array<String>) {
    val exception = kotlin.KotlinNullPointerException() //this is to validate that we have loaded the standard library
    throw exception //just for the sake of it.
}
```

3) add a test in the test/kotlin/<packagename> you want as an example you can use ExampleTest.kt

```kotlin
@RunWith(JUnit4::class)
class ExampleTest : KotlinLightCodeInsightFixtureTestCaseLighter() {
    //The folder referenced again
    override fun getTestDataPath(): String = "src/test/testData/"

    @Test
    fun tryExample() {
        myFixture.testHighlighting("Example.kt")
    }

}
```

4) Tada, it should "just work" out of the box. (if somehow its utterly broken then perhaps setting idea home system
   property might solve some issues (
   see https://plugins.jetbrains.com/docs/intellij/tests-prerequisites.html#set-the-run-configuration-parameters))

## License

Most is copy and paste from https://github.com/JetBrains/intellij-community so that is Apache2 licensed thus it all
trademarked Jetbrains. (the original file copyright has been included in each file even after modifying it to preserve
the license)



### other noteworthy things
the xml format for these test-fixtures does not allow whitespace, and will create some very obscured error messages.

valid
```
<warning descr="wee desc here">code</warning>
```

invalid
```
<warning descr ="wee desc here">code</warning>
```
(notice the space after descr).

