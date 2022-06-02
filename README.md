# untis4j - a java API for webuntis

<p align="center">
    <a href="https://github.com/ByteDream/untis4j/actions/workflows/ci.yml">
        <img src="https://github.com/bytedream/untis4j/workflows/CI/badge.svg" alt="">
    </a>
    <a href="https://smartrelease.bytedream.org/github/ByteDream/untis4j/untis4j-{tag}-withDependencies.jar">
        <img src="https://img.shields.io/github/downloads/ByteDream/untis4j/total" alt="">
    </a>
</p>

---

**[⚠️ This library is looking for a new maintainer!](https://github.com/ByteDream/untis4j/issues/12)**

---

# Introduction

**untis4j** is a rich java API for the webuntis timetable / schedule software and is inspired from the [python webuntis api](https://github.com/python-webuntis/python-webuntis)
and another [java webuntis api](https://github.com/FearlessTobi/WebUntis-Java).
It support almost all webuntis request and is easy to use. If a request method is missing,
you can easily implement the method yourself with the `Session.getCustomData(...)` [method](#Custom-request).

# Installation

### Gradle

`settings.gradle`
```kotlin
sourceControl {
    gitRepository("https://github.com/bytedream/untis4j.git") {
        producesModule("org.bytedream:untis4j")
    }
}
```

`build.gradle`
```kotlin
dependencies {
    implementation("org.bytedream:untis4j") {
        version {
            branch = "master"
        }
    }
}
```

### Jar

Just download the latest [untis4j.jar](https://smartrelease.bytedream.org/github/ByteDream/untis4j/untis4j-{tag}-withDependencies.jar) file and implement it into your project.

# Examples

To get an overview about all classes and methods, read the [docs](https://bytedream.org/docs/untis4j/).

**Note:** For the `Session.login(...)` method a server and a school name is required. To gain these you have to go to [webuntis.com](https://webuntis.com/), type in your school and choose it.
Then you will be redirected to the untis login page. The url of this page is, for example `https://example.webuntis.com/WebUntis/?school=myschool#/basic/main`.
The server is the beginning of the url `https://example.webuntis.com` and the school name is the parameter after the `?school=`, in this case it is `myschool`

### Simple timetable

```java
public class Main {
    
    public static void main(String[] args) {
        try { 
            Session session = Session.login("your webuntis username", "your webuntis password", "https://example.webuntis.com", "myschool");  // create a new webuntis session

            // get the timetable and print every lesson
            Timetable timetable = session.getTimetableFromClassId(LocalDate.now(), LocalDate.now(), session.getInfos().getClassId());
            for (int i = 0; i < timetable.size(); i++) {
                System.out.println("Lesson " + (i+1) + ": " + timetable.get(i).getSubjects().toString());
            }

            // logout
            session.logout();
        } catch (LoginException e) {
            // this exception get thrown if something went wrong with Session.login
            System.out.println("Failed to login: " + e.getMessage());
        } catch (IOException e) {
            // if an error appears this get thrown
            e.printStackTrace();
        }
    }
}
```

### Find / search a specific class

```java
public class Main {
    
    public static void main(String[] args){
        try { 
            Session session = Session.login("your webuntis username", "your webuntis password", "https://example.webuntis.com", "myschool");  // creates a new webuntis session

            Classes classes = session.getClasses();

            // get a class by its id
            // findBy(...) methods only returns one result
            System.out.println(classes.findById(1234));

            // this prints all classes which contains 's' in their name.
            // searchBy(...) methods can return multiple results / matches
            for (Classes.ClassObject classObject : classes.searchByName("s")) {
                System.out.println(classObject);
            }

            // logout
            session.logout();
        } catch (LoginException e) {
            // this exception get thrown if something went wrong with Session.login
            System.out.println("Failed to login: " + e.getMessage());
        } catch (IOException e) {
            // if an error appears this get thrown
            e.printStackTrace();
        }
    }

}
```

### Custom request

```java
public class Main {

    public static void main(String[] args) {
        try { 
            Session session = Session.login("your webuntis username", "your webuntis password", "webuntis.grupet.at", "demo_inf");
            // creates a new webuntis session

            Response response = session.getCustomData("getAMethodThatIsNotImplemented");
            // requests the custom method
            if (response.isError()) {
                // you can easily check if the response contains an error
                return;
            } else {
                // get the response...
                JSONObject responseObject = response.getResponse();
                // ...and read it
                JSONObject result = responseObject.getJSONObject("result");

                System.out.println(result.toString());
            }

            // logout
            session.logout();
        } catch (LoginException e) {
            // this exception get thrown if something went wrong with Session.login
            System.out.println("Failed to login: " + e.getMessage());
        } catch (IOException e) {
            // if an error appears this get thrown
            e.printStackTrace();
        }
    }
}
```

# Information

## Caching

Caching allows to use saved request responses.
The advantages of caching are the obviously faster answer when the same things are requested, that the server does not have to send as much data and in case of internet loss data can still be accessed.
Caching is activated by default, but can use `Session.useCache(...)` to enable / disable it.

## Building

If you want to build / compile this project yourself without gradle, the `java: Compilation failed: internal java compiler error` error may occur when using IntelliJ as your IDE.
To fix this, go to `File | Settings | Build, Execution, Deployment | Compiler | Java Compiler` and change the compiler from `Javac` to `Eclipse`.


# License

This project is licensed under the GNU Lesser General Public License v3.0 (LGPL-3.0) - see the [LICENSE](LICENSE) file for more details.
