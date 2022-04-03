DR Smart Android App
=============================

## Screenshots
| | | |
|:-------------------------:|:-------------------------:|:-------------------------:|
|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="https://raw.githubusercontent.com/DrSmartGDSC/DrSmart-Android/master/screenshots/1648738114637_100.PNG?token=GHSAT0AAAAAABQGSFXETOB2AG4BF5VPG44EYSJ5YJQ">   |  <img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738161765_100.PNG?raw=true">|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738186968_100.PNG?raw=true">|
|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738217635_100.PNG?raw=true">  |  <img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738245022_100.PNG?raw=true">|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738274159_100.PNG?raw=true">|
|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738298290_100.PNG?raw=true">  |  <img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738321229_100.PNG?raw=true">|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="https://user-images.githubusercontent.com/297678/29892310-03e92256-8d83-11e7-9b58-986dcb6f702e.png">|
## Building the Dr Smart Android App

First, clone the repo:

`git clone git@github.com:DrSmartGDSC/DrSmart-Android.git`

### Android Studio

* Open Android Studio and select `File->Open...` or from the Android Launcher select `Import project (Eclipse ADT, Gradle, etc.)` and navigate to the root directory of your project.
* Select the directory or drill in and select the file `build.gradle` in the cloned repo.
* Click 'OK' to open the the project in Android Studio.
* A Gradle sync should start, but you can force a sync and build the 'app' module as needed.

## Running the DR Smart Android App

Connect an Android device to your development machine.

### Android Studio

* Select `Run -> Run 'app'` (or `Debug 'app'`) from the menu bar
* Select the device you wish to run the app on and click 'OK'

## Using DR Smart Android App 

Please watch the demo video : https://youtu.be/KrM5eEaAhuk

## Add your own server to the App

* First you need to use our backend servcies from here : https://github.com/DrSmartGDSC/DrSmart-Backend and upload it to your cloud 
* Second Change BaseUrl From `Tools-> Utils-> AppTools and change BASEURL to url domain name`


