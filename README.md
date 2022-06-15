
WarO_Java
=========

a Java submission for War-O as a code exercise

* this project uses Java 8 streams and Java 10 `var`
* goals include: a functional style, immutable objects, minimal use of for-loops
* Spring's Java configuration is used to configure players

To Build:
---------

* requires JDK 10

useful commands:

* `./gradlew clean test`
    - on Windows, use `gradlew.bat`
* `./gradlew run`
* `./gradlew build`

See test output in `~/build/reports/tests/index.html`

See executable zip in `~/build/distributions`

To Run:
---------

* configure `src/main/java/org/peidevs/waro/config/Config.java`
* `./gradlew run`
    - on Windows, use `gradlew.bat`

Rules:
---------

Rules are [here](Rules.md).
