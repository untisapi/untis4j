**_untis4j_** - a java API for webuntis

[Click here to get the latest version of the API (dependencies included)](https://github.com/ByteDream/untis4j/releases/download/v1.1/untis4j-1.1-withDependencies.jar)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/415176566ce24467a7a3f0fbf2b932cf)](https://www.codacy.com/gh/ByteDream/untis4j/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ByteDream/untis4j&amp;utm_campaign=Badge_Grade)
[![Download Badge](https://img.shields.io/github/downloads/ByteDream/untis4j/total)](https://github.com/ByteDream/untis4j)

# Introduction

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/903fc37d59734fb5a3d5bbd071e9ff75)](https://app.codacy.com/gh/ByteDream/untis4j?utm_source=github.com&utm_medium=referral&utm_content=ByteDream/untis4j&utm_campaign=Badge_Grade_Settings)

**untis4j** is a rich java API for the webuntis timetable / schedule software and is inspired from the [python webuntis api](https://github.com/python-webuntis/python-webuntis)
and another [java webuntis api](https://github.com/FearlessTobi/WebUntis-Java).
It support almost all webuntis request and is easy to use. If a request method is missing,
you can easily implement the method yourself with the `Session.getCustomData(...)` [method](#Custom-request).

# Requirements

- Java 8 or higher
- `org.json` [java library](https://github.com/stleary/JSON-java)

# Installation

Just download the latest [untis4j.jar](https://github.com/ByteDream/untis4j/releases/download/v1.1/untis4j-1.1-withDependencies.jar) file, implement it into your project and start coding :)

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

            for (Timetable.Lesson lesson: session.getTimetableFromKlasseId(LocalDate.now(), LocalDate.now())) {  // get the timetable and loop over it
                System.out.println("Lesson " + String.join(", ", lesson.getSubjectIds()) + " from " + lesson.getStartTime() + " to " + lesson.getEndTime());  // print the subject and the lesson time (from x to y)
            }

            session.logout();  //logout
        } catch (LoginException e) {  // this exception get thrown if something went wrong with Session.login
            System.out.println("Failed to login: " + e.getMessage());
            return;
        } catch (IOException e) {  // if an error appears this get thrown
            e.printStackTrace();
            return;
        }
    }
}
```

## Get a specific klasse

```java
public class Main {
    
    public static void main(String[] args){
        try { 
            Session session = Session.login("your webuntis username", "your webuntis password", "https://example.webuntis.com", "myschool");  // creates a new webuntis session

            Klassen klassen = session.getKlassen();  // get all klassen which are registered on the server
            Klassen.KlasseObject myKlasse = klassen.findById(1234);  // find an klasse by its id

            System.out.println("Name of my klasse: " + myKlasse.getLongName());

            session.logout();  //logout
        } catch (LoginException e) {  // this exception get thrown if something went wrong with Session.login
            System.out.println("Failed to login: " + e.getMessage());
            return;
        } catch (IOException e) {  // if an error appears this get thrown
            e.printStackTrace();
            return;
        }
    }

}
```

## Custom request

```java
public class Main {

    public static void main(String[] args) {
        try { 
            Session session = Session.login("your webuntis username", "your webuntis password", "webuntis.grupet.at", "demo_inf");  // creates a new webuntis session

            Response response = session.getCustomData("getAMethodThatIsNotImplemented");  // requests the custom method
            if (response.isError()) {  // you can easily check if the response contains an error
                return;
            } else {
                JSONObject responseObject = response.getResponse();  //get the response...
                JSONObject result = responseObject.getJSONObject("result"); //...and read it

                System.out.println(result.toString());
            }

            session.logout();  //logout
        } catch (LoginException e) {  // this exception get thrown if something went wrong with Session.login
            System.out.println("Failed to login: " + e.getMessage());
            return;
        } catch (IOException e) {  // if an error appears this get thrown
            e.printStackTrace();
            return;
        }
    }
}
```

# Information

## Web Scraping

The API use [web scraping](https://en.wikipedia.org/wiki/Web_scraping) to receive the necessary data.
Theoretically, web scraping is illegal, but I've never heard of anybody getting any webuntis disadvantages because of it (ban or something like this).
You can use [caching](#Caching) to minimize your server requests and the risc of being discovered by an admin.

## Caching

Caching is available since the last few commits and allows to use saved request responses.
The advantages of caching are the obviously faster answer when the same things are requested, that the server does not have to send as much data and in case of internet loss data can still be accessed.
Caching is activated by default, but can use `Session.useCache(...)` to enable / disable it.

# Licence

This project is licensed under the GNU Lesser General Public License v3.0 (LGPL-3.0) - see the [LICENSE](LICENCE) file for more details.
