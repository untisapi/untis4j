**_untis4j_** - a java API for webuntis

[Click here to get the latest version of the API (dependencies included)](https://github.com/ByteDream/untis4j/releases/download/v1.2.1/untis4j-1.2.1-withDependencies.jar)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c416d7e79b464afbaac22e3788b00f1f)](https://www.codacy.com/gh/ByteDream/untis4j/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ByteDream/untis4j&amp;utm_campaign=Badge_Grade)
[![Download Badge](https://img.shields.io/github/downloads/ByteDream/untis4j/total)](https://github.com/ByteDream/untis4j/releases/download/v1.2.1/untis4j-1.2.1-withDependencies.jar)

# Introduction

**untis4j** is a rich java API for the webuntis timetable / schedule software and is inspired from the [python webuntis api](https://github.com/python-webuntis/python-webuntis)
and another [java webuntis api](https://github.com/FearlessTobi/WebUntis-Java).
It support almost all webuntis request and is easy to use. If a request method is missing,
you can easily implement the method yourself with the `Session.getCustomData(...)` [method](#Custom-request).

# Requirements

- Java 8 or higher
- `org.json` [java library](https://github.com/stleary/JSON-java)

# Installation

Just download the latest [untis4j.jar](https://github.com/ByteDream/untis4j/releases/download/v1.2.1/untis4j-1.2.1-withDependencies.jar) file, implement it into your project and start coding :)

# Examples

To get an overview about all classes and methods, read the [docs](https://bytedream.github.io/untis4j/).

**Note:** For the `Session.login(...)` method a server and a school name is required. To gain these you have to go to [webuntis.com](https://webuntis.com/), type in your school and choose it.
Then you will be redirected to the untis login page. The url of this page is, for example `https://example.webuntis.com/WebUntis/?school=myschool#/basic/main`.
The server is the beginning of the url `https://example.webuntis.com` and the school name is the parameter after the `?school=`, in this case it is `myschool`

## Simple timetable

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

## Find / search a specific class

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

## Custom request

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

If you want to build / compile this project yourself, the `java: Compilation failed: internal java compiler error` error may occur when using IntelliJ as your IDE.
To fix this, go to `File | Settings | Build, Execution, Deployment | Compiler | Java Compiler` and change the compiler from `Javac` to `Eclipse`.


# License

This project is licensed under the GNU Lesser General Public License v3.0 (LGPL-3.0) - see the [LICENSE](LICENSE) file for more details.
