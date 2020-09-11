**_untis4j_** - a java API for webuntis

[Click here to get the latest version of the API (dependencies included)](https://github.com/ByteDream/untis4j/releases/download/v1.0/untis4j-1.0.jar)

# Introduction

**untis4j** is a java API for the webuntis timetable / schedule software and is inspired from the [python webuntis api](https://github.com/python-webuntis/python-webuntis)
and another [java webuntis api](https://github.com/FearlessTobi/WebUntis-Java).
It support almost all webuntis request and is easy to use. If a request method is missing,
you can easily implement the method yourself with the `Session.getCustomData` [method](#Custom-request)

# Requirements

- Java 8 or higher
- `org.json` [java library](https://github.com/stleary/JSON-java)

# Installation

Just download the latest untis4j.jar file, implement it into your project and start coding :)

# Examples

To get an overview about all classes and methods, read the [docs](https://bytedream.github.io/untis4j/)

## Simple timetable

```java
import org.bytedream.untis4j.responseObjects.Klassen;import org.bytedream.untis4j.responseObjects.Timetable;public class Main {
    public static void main(String[] args) {
        try { 
            Session session = Session.login("your webuntis username", "your webuntis password", "webuntis.grupet.at", "demo_inf");  // create a new webuntis session

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
            Session session = Session.login("your webuntis username", "your webuntis password", "webuntis.grupet.at", "demo_inf");  // creates a new webuntis session

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
Theoretically, web scraping is illegal, but I've never heard of anybody getting any webuntis disadvantages because of it (ban or something like that).
You can use [caching](#Caching) to minimize your server requests and the risc of being discovered by an admin

## Caching

Caching is available since version `1.1` and allows to use saved request responses.
However, caching is slower than sending a new request to the server.
The only advantages of caching at the moment is that the server does not have to send as much data and in case of internet loss data can still be accessed.
Caching is deactivated by default. You can use `Session.useCache(...)` to enable / disable it

# Licence

This project is licensed under the GNU Lesser General Public License v3.0 (LGPL-3.0) - see the LICENSE file for more details
