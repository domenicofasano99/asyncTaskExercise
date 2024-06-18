This app was created to challenge me with the use of asyncTasks in android.
The app consists of three main functionalities:
1. A calculator operating on the main thread to perform calculations such as (addition, subtraction, division and multiplication)
2. A timer that operates in async and shows its progress via the onProgressUpdate function
3. An image download operating in async (with a 3-second timer to simulate network latency) 

The tasks in asynk operate on several threads so that several tasks can be launched at the same time without conflicting with each other,
and while the tasks are being executed, it is possible to have fun doing some simple calculations with the calculator provided
