plugins {
    packetevents.`shadow-conventions`
    packetevents.`library-conventions`
}

dependencies {
    compileOnlyApi(libs.netty)
    shadow(project(":api", "shadow"))
}
