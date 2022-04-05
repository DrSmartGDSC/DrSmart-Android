DR Smart Android App
=============================

## Google Play Beta Open Testing 

<a href='https://play.google.com/store/apps/details?id=com.gdsc.drsmart&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png'/></a>

## Screenshots
| | | |
|:-------------------------:|:-------------------------:|:-------------------------:|
|<img width="600" alt="Dr Smart" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738114637_100.PNG?raw=true">   |  <img width="600" alt="Dr Smart" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738161765_100.PNG?raw=true">|<img width="600" alt="Dr Smart" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738186968_100.PNG?raw=true">|
|<img width="600" alt="Dr Smart" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738217635_100.PNG?raw=true">  |  <img width="600" alt="Dr Smart" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738245022_100.PNG?raw=true">|<img width="600" alt="Dr Smart" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738274159_100.PNG?raw=true">|
|<img width="600" alt="Dr Smart" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738298290_100.PNG?raw=true">  |  <img width="600" alt="Dr Smart" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738321229_100.PNG?raw=true">|<img width="600" alt="Dr Smart" src="https://github.com/DrSmartGDSC/DrSmart-Android/blob/master/screenshots/1648738376971_100.PNG?raw=true">|
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
* finally debug or run the app in your android device


