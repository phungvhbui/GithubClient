

# Mini GitHub client

A mini GitHub Client that allows user to search through GitHub users and view their profiles

## Description

An Android application using GitHub API which is intergrated with different technologies and app architecture. The client has these activities:

* Search” screen with input field
* “Search results” screen with results found by user’s input
* “User details” screen with some common data about user found

## Getting Started

### Dependencies

- JDK 1.8
- Android SDK
- Latest Android SDK Tools and build tools.

### Run the application

- Get GitHub access token: [Help](https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token)

- Create a `gradle.properties` file in root folder, add this line:

  ```
  API_KEY="<GitHub access token>"
  ```

* Build and run the application

  *(\*)I'm using IntelliJ for development*

## Developer notes

### Architecture and tools

- Apollo GraphQL client
- RxJava/RxAndroid
- Paging 3

with below architecture:

![https://3.bp.blogspot.com/-WDij3mAUZEo/XxXYliprwbI/AAAAAAAAPRY/EaHnaoH0S60ydry-Q7ZPpw1L5FuPG_cnACLcBGAsYHQ/s1600/Screenshot%2B2020-07-01%2Bat%2B13.41.47.png](https://3.bp.blogspot.com/-WDij3mAUZEo/XxXYliprwbI/AAAAAAAAPRY/EaHnaoH0S60ydry-Q7ZPpw1L5FuPG_cnACLcBGAsYHQ/s1600/Screenshot%2B2020-07-01%2Bat%2B13.41.47.png)

*(\*)  [Source](https://android-developers.googleblog.com/2020/07/getting-on-same-page-with-paging-3.html)*

## Pending problems

### Bugs

* Swipe to refresh layout is not working as intented, using it the first time will restart the app automatically but works perfectly when using the restarted app.

* Network status:

  * The app will crash if trying to search when the Internet is disconnected

  * The same will happen when the Internet is disconneted and user is still scrolling to the next page

### Possible changes

* Using a better solution to hold the application state when changing orientation. Currently this problem is handled by unrecommended solution