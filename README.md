AppNotifier
============

The idea
--------  
Imagine that you want to display some messages inside your different webapps, for example, you want to alert your users that your webapp will be down between 1:00PM and 2:00PM the 5th of july.  
Will you modify your app each time you want to alert your users? No of course...  
Will you build a "notification manager" in your backend and copy it in all your webapps? Well maybe, but there is a better solution, the AppNotifier!

Getting started
---------------
After cloning this project, you just have to execute `mvn clean install` on the parent project. You will get two webapps:
- **appNotifier-admin-middle.war** in *appNotifier-admin-middle/target*
- **appNotifier-public-middle.war** in *appNotifier-public-middle/target*

Deploy them in your favorite application container and enjoy!
