### Flow Diagram ###

Class associated with each block in the flow diagram is given in the bracket.

![http://event-reminder.googlecode.com/svn/wiki/images/780flowDiagramUpdated.png](http://event-reminder.googlecode.com/svn/wiki/images/780flowDiagramUpdated.png)


### Details about Main classes ###

**MainActivity.java**

This is the class for main screen of the appication. It lists scheduled events. User can view list of scheduled tasks on the main screen. It starts AddReminder activity to add new event and to edit event. For edit event, it passes rowId of an event as an extra information to
AddReminder activity.It also creates context menu to delete event.

http://code.google.com/p/event-reminder/source/browse/trunk/EventReminder/src/com/example/android/eventreminder/MainActivity.java


**AddReminder.java**

This class is used to add and edit the event. It uses data from incoming intent to decide whether it is request to add new event or edit event. It calls AlarmManager’s set() method to set time-based reminder. It calls LocationManager's addProximityAlert() method to set location-based reminder.

http://code.google.com/p/event-reminder/source/browse/trunk/EventReminder/src/com/example/android/eventreminder/AddReminder.java

**EventReminderManager.java**

This class is responsible for setting up alarms using the AlarmManager class It creates new Intent object which specifies what should happen when the alarm goes off. (In this case AlarmReceiver receiver should be called). A PendingIntent is created which helps AlarmManager to notify an application that an action needs to be performed. The Pending-Intent contains the newly created Intent object.

http://code.google.com/p/event-reminder/source/browse/trunk/EventReminder/src/com/example/android/eventreminder/EventReminderManager.java

**EventLocationManager.java**

This class is responsible for adding proximity alert using the LocationManager class. It creates new Intent object which specifies what should happen. (In this case LocationReceiver receiver should be called). A PendingIntent is created which helps LocationManager to notify an application that an action needs to be performed. The Pending-Intent contains an newly created Intent object.

http://code.google.com/p/event-reminder/source/browse/trunk/EventReminder/src/com/example/android/eventreminder/EventLocationManager.java

**AlarmReceiver.java**

AlarmReceiver is an instance of BroadcastReceiver. When the pending intent is broadcasted by the AlarmManager, this class responds to the intent. It accepts the intent, locks the CPU, and performs the necessary work. It starts EventReminderService.

http://code.google.com/p/event-reminder/source/browse/trunk/EventReminder/src/com/example/android/eventreminder/AlarmReceiver.java

**LocationReceiver.java**

LocationReceiver is an instance of BroadcastReceiver. When the pending intent is broadcasted by the LocationManager, this class responds to the intent. It accepts the intent, locks the CPU, and performs the necessary work. It starts EventReminderService

http://code.google.com/p/event-reminder/source/browse/trunk/EventReminder/src/com/example/android/eventreminder/LocationReceiver.java

**EventReminderService.java**

This class is responsible for notification when an alarm and proximity alert are fired. Sets up pending intent which is used by notification system. Pending intent contains intent to start ViewReminder Activity when notification is clicked.

http://code.google.com/p/event-reminder/source/browse/trunk/EventReminder/src/com/example/android/eventreminder/EventReminderService.java

**ViewReminder.java**

> When the notification is clicked, ViewReminder Activity is started. It displays title and notes for the corresponding event. It also gives option to delete the event. For, time based reminders, it offers snooze functionality

http://code.google.com/p/event-reminder/source/browse/trunk/EventReminder/src/com/example/android/eventreminder/ViewReminder.java