plugins {
        id("com.android.application")
            id("org.jetbrains.kotlin.android")
}

android {
        namespace = "com.sakib.termuxbluetooth"
            compileSdk = 36

                defaultConfig {
                            applicationId = "com.sakib.termuxbluetooth"
                                    minSdk = 29
                                            targetSdk = 36
                                                    versionCode = 1
                                                            versionName = "1.0"
                }

                    buildTypes {
                                release {
                                                isMinifyEnabled = false
                                }
                    }

                        compileOptions {
                                    sourceCompatibility = JavaVersion.VERSION_25
                                            targetCompatibility = JavaVersion.VERSION_25
                        }

                            kotlinOptions {
                                        jvmTarget = "21"
                            }
}
                                }
                }
}