# Overview #

> I am developing an event reminder android application for the CSC 780 term project.  The event reminder application allows users to create a list of their tasks/events and associate reminder time with every task.  When the task is due, it will notifies the user. In addition to this basic reminder feature, this event reminder app will have three extra features. First, it will allows the user to set the location for event/tasks. The user will be notified about event when he/she is near to the selected location. Second, The user can record voice memos and use it as an event reminder alert tone. Finally, the user can take the picture and use it as an event detail.

> The target audience for this application is all the android users. Any android user who wants to schedule reminder for important events/tasks can use this application. The user must be familiar with android handset features such as go to home screen using the home button, use back button to return to the previous application etc. The location alert feature requires that the user must know how to use Google maps.


# Use Cases #

**1. The user creates and updates the event**

> _John is a computer science graduate student at SFSU. He has taken four courses in this semester and doing part time job also. His schedule is so hectic that it's hard to keep track of all the deadlines. He forgot the deadline of one of the assignments and that resulted into 50% grade penalty for that assignment. John comes across this event reminder android application. He decides to use it to organize his tasks and deadlines_.

> John wants to add new event. From the main screen, he clicks on the "+" button to create new event. John wants to create event about tomorrow's seminar. He enters the title, "ZINC seminar". He enters the due-date, 21st Sep 2011  and due-time 5:00 pm. Then he clicks on the alarms field which takes to the alarm screen. On the alarm screen, he marks the check box in front of option, "1 hour before" and choose the ring type "Ring at once". In the notes, field he added the room number "TH 331". He saves the event by clicking on the save button. This takes him to the main screen where he can now see that his event is listed.

> John later finds that the seminar is going to start half an hour early, at 4:30 pm. He starts the application. Click on the "ZINC seminar" event in the list. This opens an edit screen. He changes the due time to 4:30 pm and saves the application.

> On the next day, John finishes his class at 3:30 pm and is leaving for home. But he receives reminder alert about the seminar at 3:30pm. So, he decides to stop and attain the seminar.
**Actions**

1)User creates an event

2)User enters the event details

3)User saves the event

4)User update/edit the event details (optional)

5)User saves the event

6)User receives reminder when the alarm is due.



**2. The user receives location alert**

> John is very happy with the event reminder application. He decides to add location updates to some of his events. He edits the "Visit Library" event by clicking on it and then click the "Location" field in the edit screen. This opens the Google map. John selects the J Paul Leonard Library at SFSU location and add it to the event and saves the event.
> After two days, he goes to the Bursar office to pay lab fees at administrator building which is close to the library. He receives location reminder alert about the "Visit Library". After paying the fees, John goes to the library.

**Actions**

1) User creates/edits the event

2) User clicks the "Location" field

3) User selects location on the Google map

4) User adds the location to the event

5) User saves the event

6) User receives Location alert reminder


**3. The user adds photo to the event**

> Mary is John's girlfriend. Her birthday is in the next week. John don't understand what to gift her. He meets his sister and asks for gift ideas. His sister shows her wrist-watch that she bought couple of days ago. John really likes that watch and decide to buy the similar one as a gift. He creates an event "Buy gift for Mary" and enters due day, "Saturday" and due time, 10:30 am. He clicks the picture field. A camera is launched. He takes the picture of the wrist-watch, clicks the use button and saves the event.

> On Saturday morning, John went to the mall for his shopping. He receives alert about birthday gift. He clicks on the picture in the alert dialog and open it. He goes to the shop, finds the wrist-watch using picture and buy that watch for Mary.

**Actions**

1) User creates (or edit existing) event

2) User adds/updates the event details

3) User clicks the "Picture" field

4) User takes picture

5) User uses the picture taken for the event

7) User saves the event

8) User receives an alert

9) User clicks the picture in the alert dialog and open it


**4. The user add voice memo as an alert tone**

> John's school friend Peter is shifted to San Francisco from LA. John meets him on Sunday. Peter invited John to his home after two weeks. John creates reminder event at that time for their next meeting. He enters the necessary details and click on the "Voice memo" field. The audio recorder is launched. John clicks the "record" button, record the voice memo in the Peter's voice and click "use" button to set that voice memo as reminder alert tone. John receives reminder alert about visiting Peter and the alarm tone is the Peter's voice.

**Actions**

1) User creates (or edit existing) event

2) User adds/updates the event details

3) User clicks the "Voice memo" field

4) User takes records the voice memo

5) User use the recording.

7) User saves the event

8) User receives alert with voice memo as an alarm tone


# Requirements and their priorities #

The requirements are divided into following categories:
  * Priority 1(P1): Must have
  * Priority 2(P2): Important and desired
  * Priority 3(P3): Opportunistic

**a) List view of the events** (P1):

The user shall be able to see the lists of the events on the main screen of the application.

**b) Add/Create Event** (P1) :

The user shall able to create event using "+" button on the main screen.

**c) Edit Event** (P1):

The user shall be able to edit the event by clicking on event in the main screen's list. The user shall be able to update any field.

**d) Delete Event** (P1):

The user shall be able to delete the event. The user shall mark the check box for the event in the list on the main screen. This popups a dialog box asking to delete the event.

**e) Add Title and Note** (P1):

The user shall be able to type "Title" and "Note" of the event using on screen keyboard.

**f) Set Date and Time** (P1):

The user shall be able to set due date and due time to the event.

**g) Set priority** (P1):

The user shall be able to set the "highest" or "High-medium" or "medium-low" or "low" priority to the event.

**h) Set Alarm** (P1):

The user shall be able to choose when to be notified using "Alarm" screen. The user shall select the "Alarms" field on add/edit screen which will open the alarm's screen where the user shall have options to choose multiple alarms.

**i) Set Ring/Vibration Type** (P3):

The user shall be able to choose a ring type between "Ring at once" and "Ring until dismiss". If the type is "Ring at once" then alert-tone/vibration will occur single time and then dismissed. If the type is "Ring until dismiss", then it will wait for user to dismiss the alert-tone/vibration.

**j) Add Location** (P1):

The user shall be able to choose the location on the Google map and add it to the event in order to receive the location alerts.
> The user will click on the "Location" field on the add/edit screen to see the Google map. Then he/she will be able to zoom in or zoom out the map, selects the location by touching and add it using add button.

**k) Add Voice** (P2):

The user shall be able to record voice memos and use them as alert tone for the reminder.
> The user will click "Voice memo" field on the add/edit screen to launch the audio recorder. On the recording screen, the user will have options to record, play, stop and use the recording.

**l) Add Picture** (P3):

The user shall be able to take a picture and add to the event.
> The user will click on the "Picture" field of the add/edit screen to launch the camera and take a picture.

**m) Alert Dialog box** (P1):

The dialog window shall be pop up on the alarm time. The dialog box shall contain the title, notes and picture(if any) of the current event. The user shall be able to snooze the alarm.

**n) Location alert** (P1):

The dialog box shall be open when the user is close to the event's location. The dialog box contents for location alert will be similar to the alert dialog.

**o) View Picture** (P3):

The user shall be able to see the picture by clicking the image on the dialog box.



# UI mockups #


**_1) Welcome Screen_**

This will be the main screen of the application. It lists all the events/tasks. For each event, Title, priority, due date and due tome is present on the main screen.

The user will add new event/task using "+" button on the main screen. The name, priority, due date and due time for each task will be available on the main screen.

There will be a check box present in front of every task. The user will be able to mark the check box and clear the task from the list. The user can edit event by clicking on it.

![http://event-reminder.googlecode.com/svn/wiki/images/1.png](http://event-reminder.googlecode.com/svn/wiki/images/1.png)

**_2) Add Event_** and **_Edit Event_**

For each new event/task, Title, Priority, Due date, Due time, Alarms, Notes, Picture, Location and Voice memo fields shall be present. The mandatory fields are indicated by red stars.

The first entry field for new event shall be 'Title'. The user shall enter the name of the event. The next field priority shall have four options:
  * Highest priority is represented by 4 stars
  * High-Medium priority is represented by 3 stars
  * Medium-low priority is represented by 2 stars
  * Low priority is represented by 1 star
The user can associate priority accordingly to each event/task.

Then user shall be able to select 'Due date' and 'Due time' for an event. The 'Alarms' field allows user to choose when to be notified.

Additional details about the event shall be added to the 'Notes' field. for example, if the events is about the dinner at the restaurant, the user can add address of the restaurant to the notes.

The next three fields on the add screen is to add picture, location and voice memo to the event details.

The edit screen has same field as the add screen. Default values shall be present for the previously saved fields. The user can edit all the fields and update the event details.


![http://event-reminder.googlecode.com/svn/wiki/images/2a.png](http://event-reminder.googlecode.com/svn/wiki/images/2a.png)

**_3) Alarms_**

The alarm screen has several options e.g. "1 hour before" means notify the user one hour before the due time of the event.
The user can set multiple alarms using this screen. The user will have option to choose ring/vibration type.

![http://event-reminder.googlecode.com/svn/wiki/images/9.png](http://event-reminder.googlecode.com/svn/wiki/images/9.png)


**_4) Alert_**

The alert dialog will appear when the alarm is due. It shows the 'Title', 'Notes' and 'Picture'(if any) of the event. The "OK" button will close the dialog box. The "SNOOZE" button will give options to the user to postpone alarm.

![http://event-reminder.googlecode.com/svn/wiki/images/7.png](http://event-reminder.googlecode.com/svn/wiki/images/7.png)


**_5) Add Picture_**

The user shall be able to add picture to the event using 'Picture' option on the Add/Edit screen.

When the user click on this application, camera will start. The user can take the picture and use it for the event. There shall be an option present for retaking the picture.

![http://event-reminder.googlecode.com/svn/wiki/images/6.png](http://event-reminder.googlecode.com/svn/wiki/images/6.png)

**_6) Add Location_**

The user shall be able to link location with event/task using 'Location' field.

When user clicks on the 'Location' field, a map screen will open. The user will able to zoom in and zoom out the map and can pinpoint location on the map by touching and add it.

The GPS functionality will be used to track the user's location. When the user is significantly near to the pinpointed location, the user shall receive a location alert.

![http://event-reminder.googlecode.com/svn/wiki/images/3.png](http://event-reminder.googlecode.com/svn/wiki/images/3.png)

**_7) Location Alert_**

The location alert dialog shall have similar fields as the reminder alert dialog.

![http://event-reminder.googlecode.com/svn/wiki/images/8.png](http://event-reminder.googlecode.com/svn/wiki/images/8.png)

**_8) Add Voice Memo_**

'Voice Memo' field allows user to use voice recordings as their reminder alert tone.

When user clicks on the 'Voice memo' field, the voice recorder will open. It will have four options: Record, stop, play and use. Using these options, the user can record the voice messages and set as reminder tone.

![http://event-reminder.googlecode.com/svn/wiki/images/5.png](http://event-reminder.googlecode.com/svn/wiki/images/5.png)



# Storyboards #


**Storyboard 1 : How to create event**

1)Click on the "+" button on the main screen

2)Fill the event details. The mandatory fields are indicated by red star

3)Click "Save" button to save the event.

Alert dialog will appear when alarm time is due.

![http://event-reminder.googlecode.com/svn/wiki/images/1.png](http://event-reminder.googlecode.com/svn/wiki/images/1.png)
![http://event-reminder.googlecode.com/svn/wiki/images/2a.png](http://event-reminder.googlecode.com/svn/wiki/images/2a.png)
![http://event-reminder.googlecode.com/svn/wiki/images/7.png](http://event-reminder.googlecode.com/svn/wiki/images/7.png)


**Storyboard 2 : How to add location with the event**

1)Click on the "+" button on the main screen to create new event.
> OR click the event name on the main screen to edit event.

2)Fill/update the event details.

3)Click the "Location" field

4)Select location on the Google map

5)Click "ADD" button to add the location to the event

6)Click "Save" button to save the event.

Location Alert Dialog will appear when user is near to the selected location. Alert dialog will appear when alarm time is due.

![http://event-reminder.googlecode.com/svn/wiki/images/1.png](http://event-reminder.googlecode.com/svn/wiki/images/1.png)
![http://event-reminder.googlecode.com/svn/wiki/images/2a.png](http://event-reminder.googlecode.com/svn/wiki/images/2a.png)
![http://event-reminder.googlecode.com/svn/wiki/images/3.png](http://event-reminder.googlecode.com/svn/wiki/images/3.png)
![http://event-reminder.googlecode.com/svn/wiki/images/2a.png](http://event-reminder.googlecode.com/svn/wiki/images/2a.png)
![http://event-reminder.googlecode.com/svn/wiki/images/8.png](http://event-reminder.googlecode.com/svn/wiki/images/8.png)
