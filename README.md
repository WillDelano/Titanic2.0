#Titanic2.0
Software Engineering 3471 Project

1. All classes should have block comments describing what they do/how they should be used.
2. All functions should have block comments in the style of Hamerly's projects.
3. All code that is not self-explanatory should have comments documenting what it does.

Ex.
<pre>
    //finds the user with the correct name
    for (User a : list) { 
	   if (a.getName() == functionParameter.getName()) { return a; } 
    }
</pre>
    
4. Single function responsibility - A function should ideally have one responsibility. Avoid functions that do too much.

Things to keep in mind:
   When working with the UI, use a Controller class. A Controller receives calls 
   from the UI (buttons, etc.) and decides who to call in the backend before it 
   returns results from the backend to the frontend. Typically one per event, 
   but if there is lots of overlap in events, they can share a controller.

   Low Coupling - Measure of how strongly one element is connected to, and has
   knowledge of, or relies upon other elements.

   High Cohesion - Measure of how strongly related and focused the responsibilities of
   an element are.

   Law of Demeter - If two classes have no other reason to be directly aware of each
   other or otherwise coupled, then the two classes should not directly interact.

Comment Templates:

<pre>
/*
 * file: test.java
 * author: William Delano
 * date: 8/11/23
 *
 * This file contains the code for ... It should be used as an attribute for
 * *ClassName* in order to achieve ... 
 */
</pre>

<pre>
/*
 * getName
 *
 * This function returns the name of a User
 *
 * parameters:
 *     user -- the user to return the name of
 * return value: the name of the user
 */
</pre>
