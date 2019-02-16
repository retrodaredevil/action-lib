# action-lib
A set of classes to design a command or action based system around

# How it works
Everything is based off the ```Action``` interface. The interface looks like this:
```
interface Action {
    void update();
    void end();
    boolean isDone();
    boolean isActive();
}
```
This allows for a lot of flexibility on how each Action works.

The ```Actions``` class with static methods allows you to create actions to do different things
```
Actions.createRunForever(() -> System.out.println("Running!"));        // An Action that runs code every time its updated
Actions.createRunOnce(() -> System.out.println("Running!"));           // An Action that runs once, then is "done"
Actions.createWaitToStart(<my action>, () -> <my condition to start>); // runs an action when a condition is met
Actions.createActionChooser(WhenDone.DO_NOTHING);                      // switch between and update actions without ugly null checks
new Actions.ActionMultiplexerBuilder(<initial actions>).build();       // Update multiple Actions using a single action
new Actions.ActionQueueBuilder(<initial actions>).build();             // queue up Actions to update; One updates at a time
```
Each builder has methods to call to customize the behaviour of each action. There are similar methods to
allow action "Recycling." Recycling is usually recommended to be disabled so you usually have to explicitly allow it.
Recycling allows an action to be updated, ended, then updated again. 

For some implementations, by default updating, then ending, then updating again (recycling) will 
throw an exception to let you know something is being recycled when that may not be desired.

## Creating your own action
Because of how basic the ```Action``` interface is it gets tedious to add functionality to each ```Action```. 
This is where ```SimpleAction``` comes in.

```SimpleAction``` allows you to override methods such as ```onStart```, ```onUpdate```, ```onEnd```, 
and ```onIsDoneRequest```. It handles being active, starting, and has a more abstract approach to ```isDone()```.

## Useful Interfaces
While the ```Action``` interface is the most used interface, there are also other useful interfaces.

```ActionMultiplexer``` represents an action that holds and updates many actions. There is a builder to create one
in the ```Actions``` class.

```ActionQueue``` represents an action that updates a single action at a time while holding other actions on a queue.
There is a builder to create one in the ```Actions``` class.

```ActionChooser``` represents an action that updates a single action at a time. You call setNextAction() at any
time to end and change the active action. There is a factory method to create this in the ```Actions``` class.

### Coding Conventions Used in This Project
* Parameters to be passed to super methods/constructors should be put before other parameters
* Use tabs instead of spaces, 2 tabs for continued line indent. (Only for *.java files)
* Reference interfaces instead of concrete classes in method signatures as much as possible
### TODO
* Nothing