## Week 1 (9/29 - 10/6) ##
**Completed:**

The list View of the events is created.

Implemented option menu to add new event and help

The layout of the Add/update Screen is done

The click of the list items is implemented.

Initial import of the project in the Google code is done


**Activity:**

> MainActivty : This is the main activity of the application.

> AddReminder activity:The activity to add/update event/reminder

**Classes:**
> "MainActivity" inherited from ListActivity. It addresses main.xml layout. Option menu methods, "onCreateOptionsMenu" and "onMenuSelected" are added. Also method to handle the clicks on the list item is added. An intent is added to start AddReminder activity.

> "AddReminder" inherited from activity. It references add\_reminder.xml layout

**Layouts added:**

main.xml: listview is added to show the list of the events on the main screen.Text view is added. It will be used to display "No Event" message when the list is empty.

add\_reminder.xml: This is the layout for adding new event and updating event. Linear layout is used.

list\_row.xml: Layout for each row in the list.

menu.xml: layout for menu. Add event and Help options.


**Use of fake data:**
> The database has not built yet. Therefore, fake data is used for testing listview.
> An array of strings is created. ArrayAdapter is used to manage the listview.


**Screenshots:**

List View

![http://event-reminder.googlecode.com/svn/wiki/images/week1.1.png](http://event-reminder.googlecode.com/svn/wiki/images/week1.1.png)


Menu to add event

![http://event-reminder.googlecode.com/svn/wiki/images/week1_3.png](http://event-reminder.googlecode.com/svn/wiki/images/week1_3.png)


Add/Update Event screen

![http://event-reminder.googlecode.com/svn/wiki/images/week1_2.png](http://event-reminder.googlecode.com/svn/wiki/images/week1_2.png)

**Next Tasks**

1) Create New Event

2) Add date and time functionality

3)Show the event in the List view on the main screen

4) Add update/edit functionality