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
