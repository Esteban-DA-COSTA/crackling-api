# Object Models &amp; Structure

## Object Models

In Crackling, we use several object to hold the business logic.

- [](User.md): Define a user who have access to the application.
- [](Team.md): Define a project and regroup `members` and `tasks`.
- [](Member.md): A member is a `User` linked to a `Team`. Member hold information from a `User` but also team-scpecific information.
- [](Task.md): Define a piece of work in a project.

### Schema

```mermaid
classDiagram
    class User {
        +String username
        +String email
        +String password
    }

    class Member {
        +String role
        +Project team
    }

    class Project {
        +String name
        +String description
        +List~Member~ members
        +List~Task~ tasks
    }

    class Task {
        +String name
        +String description
        +Member assignee
        +TaskStatus status
        +int userPoints
    }

    class TaskStatus {
        <<Enumeration>>
        TODO
        IN PROGRESS
        DONE
    }

    User "1" <|-- "*" Member
    Member "1..*" *-- "1" Project
    Task "*" *-- "1" Project 
    Task --> TaskStatus
```

## Structure

### File structure

### Logical structure

The logical structure of the API is based on HATEOAS.