# untis4j - a java API for webuntis

<p align="center">
    <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/untisapi/untis4j/maven_build.yml?label=Github%20Build">
    <img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/untisapi/untis4j/maven_publish.yml?label=Github%20Publish">
    <img alt="Jitpack Deploy Status" src="https://img.shields.io/github/actions/workflow/status/untisapi/untis4j/maven_publish.yml?label=Jitpack%20Deploy">
    <img alt="GitHub all releases" src="https://img.shields.io/github/downloads/untisapi/untis4j/total?label=Downloads">
</p>

---

**[⚠️ This library is looking for a new maintainer!](https://github.com/untisapi/untis4j/issues/12)**

---

# Introduction

**untis4j** is a rich java API for the webuntis timetable / schedule software and is inspired from the [python webuntis api](https://github.com/python-webuntis/python-webuntis)
and another [java webuntis api](https://github.com/FearlessTobi/WebUntis-Java).
It support almost all webuntis request and is easy to use. If a request method is missing,
you can easily implement the method yourself with the `Session.getCustomData(...)` [method](#Custom-request).

# Installation

### Maven

POM
```XML
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.LuftigerLuca</groupId>
        <artifactId>untis4j</artifactId>
        <version>RELEASE</version>
    </dependency>
</dependencies>
```

### Gradle

Groovy DSL
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
dependencies {
    implementation 'com.github.untisapi:RELEASE'
}
```

Kotlin DSL
```kotlin
repositories {
    maven { url='https://jitpack.io' }
}
dependencies { 
    implementation ("com.github.untisapi:RELEASE")
}
```

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


# License

This project is licensed under the GNU Lesser General Public License v3.0 (LGPL-3.0) - see the [LICENSE](LICENSE) file for more details.
