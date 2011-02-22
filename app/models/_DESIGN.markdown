When creating a new model entity
================================

Account-related entities
------------------------
Account-related entities need to subclass CompositeModel and implement AccountModel.

They need to declare the following uniqueness constraint:

    @Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"naturalId", "account_id"})})

And they need to define the following relationship:

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Account account;

    public Account getAccount() {
        return account;
    }

Project-related entities
------------------------
Project-related entities need to subclass CompositeModel and implement ProjectModel.

They need to declare the following uniqueness constraint:

    @Table(uniqueConstraints = {@UniqueConstraint(name="id", columnNames = {"naturalId", "project_id"})})

And they need to define the following relationship:

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, optional = false)
    public Project project;

    public Project getProject() {
        return project;
    }


Design decisions
================

Primary keys for "composite" entities
-------------------------------------

Composite entities such as project-related and account-related entities have in fact a composite primary key: they are unique given one project and one natural ID.

We want to have natural IDs (starting at index 1) for entities so that our users get "user-friendly" or "natural" IDs in the URLs, of the kind

    http://foo.oxiras.com/project/1

instead of

    http://foo.oxiras.com/project/48746947


We can implement this in two ways:

### Real composite primary key
This would ensure we have a composite PK at database-level. On the JPA/Hibernate side, this can be achieved by implementing an @Embeddedable representing the composite identifier and using
it in the classes via the @EmbeddedId annotation.

### Generated identifier & multi-column uniqueness constraint on non-nullable columns
The unicity is provided by a multi-column uniqueness constraint and a not null constraint on each one o the fields.
Additionally, a unique identifier for the table serves as primary key



#### Decision
Play! does not handle well the usage of @EmbeddedId and its CRUD module does not support such cases. Also, creating HQL queries selecting entities with composite identifiers
is cumbersome to say the least (you need to create an Embeddable identifier instance for each query). Thus, we will use the approach with a generated primary key which will also allow us to use
the CRUD module if necessary for administration purposes. If there is a need for it, we can still switch to a composite primary key on the database level later on.
