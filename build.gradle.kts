buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // આપણે વર્ઝન 8.1.1 થી વધારીને 8.2.0 કર્યું છે
        classpath("com.android.tools.build:gradle:8.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
