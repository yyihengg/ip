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

## Loads Existing Tasks

Aim: Check that the chatbot loads saved todo, deadline, and event tasks from the data file when it starts.

Initial data file:
```text
T | 1 | read book
D | 0 | return book | Sunday
E | 0 | project meeting | Mon 2pm | 4pm
```

Inputs:
```text
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
Here are the tasks in your list:
1. [T][X] read book
2. [D][ ] return book (by: Sunday)
3. [E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

Expected data file:
```text
T | 1 | read book
D | 0 | return book | Sunday
E | 0 | project meeting | Mon 2pm | 4pm
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

Expected data file:
```text
T | 0 | borrow book
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

Expected data file:
```text
D | 0 | return book | Sunday
```

## Rejects Deadline Without Date

Aim: Check that a deadline command without /by shows the missing deadline date message.

Inputs:
```text
deadline return book
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
Oops! You did not provide a date for the deadline
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

Expected data file:
```text
E | 0 | project meeting | Mon 2pm | 4pm
```

## Rejects Event Without Start

Aim: Check that an event command without /from shows the missing event start date message.

Inputs:
```text
event project meeting /to 4pm
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
Oops! You did not provide a start date for the event
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Rejects Event Without End

Aim: Check that an event command without /to shows the missing event end date message.

Inputs:
```text
event project meeting /from Mon 2pm
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
Oops! You did not provide an end date for the event
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

Expected data file:
```text
T | 0 | borrow book
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
Valid commands include "list, todo, event, deadline, mark, unmark, delete"
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

## Deletes Task And Renumbers List

Aim: Check that delete removes the requested task and that list displays the remaining tasks with updated numbering.

Inputs:
```text
todo read book
deadline return book /by Sunday
event project meeting /from Mon 2pm /to 4pm
delete 2
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
[T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
[D][ ] return book (by: Sunday)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
[E][ ] project meeting (from: Mon 2pm to: 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've removed this task:
    [D][ ] return book (by: Sunday)
Now you have 2 tasks in the list ^^.
____________________________________________________________
____________________________________________________________
Here are the tasks in your list:
1. [T][ ] read book
2. [E][ ] project meeting (from: Mon 2pm to: 4pm)
____________________________________________________________
____________________________________________________________
BaiBai! Hope to see you soon ^^
____________________________________________________________

```

Expected data file:
```text
T | 0 | read book
E | 0 | project meeting | Mon 2pm | 4pm
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
