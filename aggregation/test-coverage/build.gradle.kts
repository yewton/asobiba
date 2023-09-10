plugins {
    id("net.yewton.asobiba.report-aggregation")
    // ↓が無いと Spring dependency plugin に依存してる依存関係を解決できない
    id("net.yewton.asobiba.kotlin-spring-boot-dependency")
}

dependencies {
    // 指定したプロジェクトとその依存プロジェクトのカバレッジを推移的に収集する
    aggregate("net.yewton.asobiba.example-app:app")
    aggregate("net.yewton.asobiba.osunaba:challenge")
    aggregate("net.yewton.asobiba.osunaba:mondai")
    aggregate("net.yewton.asobiba.osunaba:reactive-webapp")
}
