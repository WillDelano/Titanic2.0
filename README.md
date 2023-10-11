# Titanic2.0
Software Engineering 3471 Project

1. All classes should have Javadoc block comments.
2. All functions should have Javadoc block comments.
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

# Javadoc:

## Classes:
<pre>
/**
 * Class Description...
 *
 * More detailed description of the class, including its purpose, usage, and any other relevant information.
 *
 * @author Author Name
 * @version Version Number
 * @see Related Class or Method
</pre>

## Functions:
<pre>
/**
 * This method adds two integers and returns the result.
 *
 * @param num1 The first integer to be added.
 * @param num2 The second integer to be added.
 * @return The sum of num1 and num2.
 */
</pre>
