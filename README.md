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

### Things to keep in mind
When working with the UI, use a Controller class. A Controller receives calls 
from the UI (buttons, etc.) and decides who to call in the backend before it 
returns results from the backend to the frontend. There is typically one per event, 
but if there is lots of overlap in events, they can share a controller.

**Use Comments Sparingly** - Inline comments should be used sparingly, focusing
 on the most critical or complex parts of the code. Focus on clean, readable code
that doesn't require excessive commenting.

**Low Coupling** - Measure of how strongly one element is connected to, and has
knowledge of, or relies upon other elements.

**High Cohesion** - Measure of how strongly related and focused the responsibilities of
an element are.

**Law of Demeter** - If two classes have no other reason to be directly aware of each
other or otherwise coupled, then the two classes should not directly interact.

# Javadoc:
### Creating/updating documentation
Javadoc allows you to turn comments into a searchable html site with all
our classes and functions. To create the documentation, copy the path of the
documentation folder in our project (you can use just the name if you're in the
folder that contains it on your console) and make sure all your comments are correctly 
formated as shown below. In your console, navigate to the file you want documentation
for and type `javadoc -d /path/to/output/directory *.java`. You have a couple
options for that last parameter, but it's recommended you update documentation
for all our files (everything in the java) instead of just individual ones,
in order to prevent you from missing anything.

*Here is an example on my mac using a file path for the source:*
`javadoc -d documentation java/com/core/*.java`

### Viewing documentation
To view the documentation for the project, navigate to the documentation folder
in src/main/documentation. In this folder there should be a index.html file. Open
this file on your desktop to view the Javadoc of our project. You can't open this
file on github or intelliJ because it will just show you the html code.

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
 */
</pre>

![Example:](https://i.imgur.com/oAi474a.png)
*Note: `<p></p>` is not necessary, but creates a separate paragraph for whatever
is inside. This can be helpful to discern the in-depth description from the brief one.*

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

![Example:](https://i.imgur.com/D7x3MeY.png)
