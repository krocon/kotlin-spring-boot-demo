# KotlinDemo – Kotlin + Spring Boot

Demo-Projekt zur Video-Serie **„Kotlin statt Java? – Modernes Backend mit Spring Boot“**:
eine kleine REST-API für Aufgaben (Tasks), mit Unit-Tests, HTTP-Integrationstests und CI.

| | |
|---|---|
| Sprache | Kotlin 2.3.21 (so liefert es der Spring Initializr; mit 2.4.21 ebenfalls grün) |
| Framework | Spring Boot 4.1.2 (Spring Framework 7, Jackson 3, Tomcat 11) |
| Build | Gradle (Kotlin DSL), Wrapper 9.9.1 |
| JDK | 25 (LTS) über die Gradle-Toolchain – z. B. Eclipse Temurin |
| Tests | JUnit Jupiter 6, AssertJ, MockMvc (Kotlin-DSL) |

Das Grundgerüst stammt unverändert vom [Spring Initializr](https://start.spring.io)
(Gradle – Kotlin, Kotlin, Spring Boot 4.1.2, Java 25, Abhängigkeit „Spring Web“).

## Voraussetzung: JDK 25

Gradle braucht zum Starten mindestens Java 17, das Projekt kompiliert mit einem JDK 25 (Toolchain).
Einen automatischen JDK-Download richtet der Initializr nicht ein. Fehlt ein JDK 25, bricht der Build ab mit
`Cannot find a Java installation on your machine … matching: {languageVersion=25 …}`.

- JDK 25 installieren, zum Beispiel Eclipse Temurin, und `JAVA_HOME` darauf setzen.
- Liegt das JDK an einem Ort, den Gradle nicht absucht (etwa Homebrews `openjdk` auf dem Mac), den Pfad nennen:
  `./gradlew build -Porg.gradle.java.installations.paths=/opt/homebrew/opt/java/libexec/openjdk.jdk/Contents/Home`
  oder dieselbe Einstellung dauerhaft in `~/.gradle/gradle.properties`.
- `./gradlew -q javaToolchains` zeigt, welche JDKs Gradle findet.

## Starten

```bash
./gradlew bootRun
```

Die API läuft dann auf `http://localhost:8080`. Die Daten liegen nur im Speicher –
nach einem Neustart ist die Liste leer.

| Methode | Pfad | Antwort |
|---|---|---|
| GET | `/api/tasks` | 200, alle Aufgaben |
| GET | `/api/tasks/{id}` | 200 oder 404 |
| POST | `/api/tasks` | 201 + `Location`, 400 bei fehlendem oder leerem `title` |
| PATCH | `/api/tasks/{id}/complete` | 200 oder 404 |

```bash
curl -i localhost:8080/api/tasks
curl -i -X POST localhost:8080/api/tasks \
     -H 'Content-Type: application/json' \
     -d '{"title": "Kotlin kennenlernen"}'
curl -i -X PATCH localhost:8080/api/tasks/1/complete
curl -i localhost:8080/api/tasks/99        # 404 als application/problem+json
```

## Testen und bauen

```bash
./gradlew test      # 10 Tests: 4 × TaskServiceTest, 6 × TaskControllerTest
./gradlew build     # kompiliert, testet, baut build/libs/kotlindemo-0.0.1-SNAPSHOT.jar
```

Der HTML-Testbericht liegt danach in `build/reports/tests/test/index.html`.
Die CI (`.github/workflows/ci.yml`) führt bei jedem Push und Pull Request dieselben Befehle aus.

## Sprachbeispiele aus Folge 1

Im Teilprojekt `intro/` – ohne Spring, jede Datei hat ein eigenes `main`:

```bash
./gradlew -q intro:dataClasses   # data class, copy, Java-Record und Java-Klasse aus Kotlin
./gradlew -q intro:nullSafety    # String vs. String?, Safe Call, Elvis-Operator
./gradlew -q intro:features      # val/var, String Templates, when, Extension Function, Smart Cast
```

## Welche Datei gehört zu welcher Folge?

| Folge | Thema | Dateien |
|---|---|---|
| 1 | Kotlin vs. Java | `intro/` |
| 2 | REST-API mit Spring Boot | `build.gradle.kts`, `src/main/` |
| 3 | Automatisiertes Testen | `src/test/`, `testLogging` in `build.gradle.kts` |
| 4 | Build und CI | `.github/workflows/ci.yml` |

## Stand der Prüfung (18.11.2026)

Gebaut und getestet unter Linux mit **JDK 21 und Gradle 8.14.3** (Toolchain dafür vorübergehend auf 21 gestellt):
`test` (10 von 10 grün), `build`, `bootRun`, alle Endpunkte mit `curl`, die drei `intro`-Aufgaben.

Auf dem Mac (macOS, Homebrew-OpenJDK 25.0.1, Gradle-Wrapper 9.9.1) läuft `./gradlew build` durch.

Noch **nicht** gelaufen: der GitHub-Actions-Workflow (nur die YAML-Syntax ist geprüft).
