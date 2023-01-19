// これを Kotlin を使うサブプロジェクトのルートで呼ばないと、以下の警告が出る:
// The Kotlin Gradle plugin was loaded multiple times in different subprojects, which is not supported and may break the build.
plugins {
    kotlin("jvm") apply false
}
