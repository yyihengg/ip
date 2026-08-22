# UI Test Plan

Record console UI test cases here. Each case must include an aim, inputs, and exact expected output.

## Starts And Exits

Aim: Check that the chatbot greets the user and exits when the user enters bye.

Inputs:
```text
bye
```

Expected output:
```text
_____ _  __ __
|  ___(_)/ _(_)
| |_  | | |_| |
|  _| | |  _| |
|_|   |_|_| |_|
____________________________________________________________
Hello! My name is Fifi ^^
How may I help?
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Adds Todo And Lists

Aim: Check that the todo command stores a todo task and list displays it with the todo marker.

Inputs:
```text
todo borrow book
list
bye
```

Expected output:
```text
_____ _  __ __
|  ___(_)/ _(_)
| |_  | | |_| |
|  _| | |  _| |
|_|   |_|_| |_|
____________________________________________________________
Hello! My name is Fifi ^^
How may I help?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
[T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1. [T][ ] borrow book
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Adds Deadline And Lists

Aim: Check that the deadline command stores a deadline task and list displays its by value.

Inputs:
```text
deadline return book /by Sunday
list
bye
```

Expected output:
```text
_____ _  __ __
|  ___(_)/ _(_)
| |_  | | |_| |
|  _| | |  _| |
|_|   |_|_| |_|
____________________________________________________________
Hello! My name is Fifi ^^
How may I help?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
[D][ ] return book (by: Sunday)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1. [D][ ] return book (by: Sunday)
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Adds Event And Lists

Aim: Check that the event command stores an event task and list displays its from and to values.

Inputs:
```text
event project meeting /from Mon 2pm /to 4pm
list
bye
```

Expected output:
```text
_____ _  __ __
|  ___(_)/ _(_)
| |_  | | |_| |
|  _| | |  _| |
|_|   |_|_| |_|
____________________________________________________________
Hello! My name is Fifi ^^
How may I help?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
[E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1. [E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Marks And Unmarks Task

Aim: Check that mark and unmark update the completion status shown by list.

Inputs:
```text
todo borrow book
mark 1
unmark 1
list
bye
```

Expected output:
```text
_____ _  __ __
|  ___(_)/ _(_)
| |_  | | |_| |
|  _| | |  _| |
|_|   |_|_| |_|
____________________________________________________________
Hello! My name is Fifi ^^
How may I help?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
[T][ ] borrow book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Nice! I've marked this task as done:
[T][X] borrow book
____________________________________________________________
____________________________________________________________
OK, I've marked this task as not done yet:
[T][ ] borrow book
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1. [T][ ] borrow book
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Rejects Invalid Command

Aim: Check that an unknown command shows the invalid command message and then continues accepting commands.

Inputs:
```text
blah
bye
```

Expected output:
```text
_____ _  __ __
|  ___(_)/ _(_)
| |_  | | |_| |
|  _| | |  _| |
|_|   |_|_| |_|
____________________________________________________________
Hello! My name is Fifi ^^
How may I help?
____________________________________________________________
____________________________________________________________
UhOh, this command is invalid, please enter a valid one!
Valid commands include "list, todo, event, deadline, mark, unmark"
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Rejects Empty Todo Name

Aim: Check that a todo command with only whitespace after the command shows the empty todo name message.

Inputs:
```text
todo    
bye
```

Expected output:
```text
_____ _  __ __
|  ___(_)/ _(_)
| |_  | | |_| |
|  _| | |  _| |
|_|   |_|_| |_|
____________________________________________________________
Hello! My name is Fifi ^^
How may I help?
____________________________________________________________
____________________________________________________________
Oops! You cannot have an empty todo name
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Rejects Empty Deadline Name

Aim: Check that a deadline command with no task name before /by shows the empty deadline name message.

Inputs:
```text
deadline    /by Sunday
bye
```

Expected output:
```text
_____ _  __ __
|  ___(_)/ _(_)
| |_  | | |_| |
|  _| | |  _| |
|_|   |_|_| |_|
____________________________________________________________
Hello! My name is Fifi ^^
How may I help?
____________________________________________________________
____________________________________________________________
Oops! You cannot have an empty deadline name
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Rejects Empty Event Name

Aim: Check that an event command with no task name before /from shows the empty event name message.

Inputs:
```text
event    /from Mon 2pm /to 4pm
bye
```

Expected output:
```text
_____ _  __ __
|  ___(_)/ _(_)
| |_  | | |_| |
|  _| | |  _| |
|_|   |_|_| |_|
____________________________________________________________
Hello! My name is Fifi ^^
How may I help?
____________________________________________________________
____________________________________________________________
Oops! You cannot have an empty event name
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```
