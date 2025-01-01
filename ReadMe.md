# Project Name: **Pet Quest**

## Demo Video

[![Watch the Demo](https://img.youtube.com/vi/VwuX3Jx733o/0.jpg)](https://www.youtube.com/watch?v=VwuX3Jx733o)


## Description
**Pet Quest** is a Java-based game application developed using Java Swing for the graphical user interface and FlexJSON for JSON data serialization/deserialization. The game provides a simulation pet care game, where the player's core experience will be taking care of a pet, including feeding, playing, and interacting, while offering players a low-stakes environment to manage routines. Some features inlcude an "evolution" mechanism, where the pets evolve based on the player’s care, affecting their appearance, animations, and behaviors and also a ...  

---

## Required Libraries and Third-Party Tools

### Libraries and Tools
- **Java Development Kit (JDK)**: Version 11 or higher
- **Java Swing**: Included with JDK 20 or higher
- **FlexJSON**: Version 3.3
- **IntelliJ IDEA**: Version 2022.3.3 (Preffered)

### How to Obtain
- **JDK**: Download from [Oracle's official site](https://www.oracle.com/java/technologies/javase-downloads.html) or use OpenJDK.
- **FlexJSON**: Download the library jar file from [FlexJSON GitHub](https://github.com/aranega/flexjson) or a trusted Maven repository.
- **IntelliJ IDEA**: [JetBrains's official site](https://www.jetbrains.com/idea/download/?section=windows)

---

## Building the Software (Compiling from Source)

1. **Install Java Development Kit (JDK)**
   - Download and install JDK 11 or higher.
   - Set the `JAVA_HOME` environment variable.
   - Add the JDK `bin` directory to your system's PATH.

2. **Download FlexJSON**
   - Download the `flexjson-3.3.jar` file and save it in the `lib` directory within your project folder.

3. **Clone the Repository**
   - Clone the source code repository:
     ```bash
     git clone ssh://git@gitlab.sci.uwo.ca:7999/courses/2024/09/COMPSCI2212/group50.git
     ```
4. **Open Java IDE (IntelliJ preffered)**

5. **Compile the Source Code**
   - Compile the source files:
     ```bash
     javac -cp lib/flexjson-3.3.jar -d bin src/*.java
     ```

6. **Package the Application**
   - Package into a JAR file (optional):
     ```bash
     jar cvfe MySwingApp.jar Main -C bin .
     ```

---

## Running the Compiled Software

### Prerequisites
- Ensure you have Java Runtime Environment (JRE) installed (JDK includes JRE).
- Ensure you have maven projects set up in order for the resources and classes to appear
- To do so find the View -> Tool Windows -> Maven Projects (or open it from left bottom corner menu) and then build your project with maven goals (i.e. package)

### Steps to Run
1. **Go to src -> main -> java -> Main.java class**

2. **Run the Main.java application**

---

### Accessing Parental Controls
1. Upon opening the main menu go to the "Parental Controls" section.
2. The password is "password"

### Features
- Set usage limits.
- Revive all pets.

---

## Notes for Teaching Assistant
- Ensure JDK is installed and paths are correctly set before compiling or running the project.
- Make sure project is set as a maven project.
- The main branch to run the application is under the name main-merged
---
