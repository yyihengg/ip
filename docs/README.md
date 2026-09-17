 # Use of AI-generated work
From course Week 3 onwards, Young Yi Heng (the developer of this project) used OpenAI Codex to support development of this project. Codex was used to plan changes and discuss ideas before generating code based on Young Yi Heng’s plans and implementation decisions.

# Fifi User Guide

## Understanding errors

When a command fails, Fifi shows an **Error** heading with a pale red background,
a dark red border, and dark red text in the graphical interface. The console
shows an `[ERROR]` heading above the same explanation.

For example, `show tomorrow` displays:

```text
[ERROR]
Oops! Please use yyyy-MM-dd for dates.
```

This applies to unknown commands, missing arguments, invalid dates, invalid task
numbers, exceeding the task limit, and failures to save tasks. Read the explanation,
correct your command, and send it again. Successful responses use the normal style.
If saving fails, the task list and its completion timestamps remain unchanged.
Check file access and available disk space, then retry the command. Saves replace
the old file atomically; environments that cannot support atomic replacement
report an error and preserve the old save file.

If saved tasks cannot be loaded, Fifi displays a startup warning. Task changes are
blocked to protect the existing file. Make a backup, correct the reported data-file
problem, and restart Fifi. Read-only commands remain available, but they show an
empty list while loading has failed. A missing save file is normal on first use.

## Entering commands and task details

Fifi accepts leading and trailing spaces, multiple spaces, and tabs between command
parts. `list` and `bye` accept no arguments. Task numbers start at 1. Dates use
`yyyy-MM-dd`, with a year from 0001 through 9999 and a real calendar date.

Deadline commands require one separate `/by` parameter. Event commands require
one `/from` followed by one `/to`; the end date must be on or after the start date.
Same-day events are allowed. Repeated, glued, and misplaced date parameters produce
errors. For example:

```text
deadline return book /by 2026-09-20
event project meeting /from 2026-09-20 /to 2026-09-21
```

Descriptions cannot be blank or contain `|` or control characters other than tabs.
Ordinary punctuation and Unicode text are allowed. Fifi rejects a new task with the
same type, trimmed case-sensitive description, and dates as an existing task.
Completion status and timestamps do not make a task different. Different task types
or dates are allowed, and existing duplicates in older save files remain readable.
Delete an existing task before adding the same details again.

If marking a task would record completion before its saved creation time, Fifi
leaves the task unchanged and asks you to check the system clock or correct the
saved timestamp and restart. This prevents clock changes from corrupting task history.

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Feature ABC

// Feature details


## Feature XYZ

// Feature details
