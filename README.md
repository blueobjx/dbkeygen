# dbkeygen

Generate Hibernate implicit naming strategy names for index, unique, and foreign key constraints.

Uses [Hibernate implicit naming strategy](https://docs.hibernate.org/orm/5.6/javadocs/org/hibernate/boot/model/naming/ImplicitNamingStrategy.html)
/ [Baeldung doc](https://www.baeldung.com/hibernate-naming-strategy#bd-implicit_name)
to generate names for indexes.  This is useful for generating SQL scripts for databases where you
want to use the implicit naming strategy versus the explicit naming strategy.

## Requirements

Application built and runs on Java 21.  Updating for newer JDK will need jakarta libs.

## Usage

**Index key**

```bash
dbkeygen index tablename columnname [columnname2 ...]
```

**Unique key**

```bash
dbkeygen unique tablename columnname [columnname2 ...]
```

**Foreign key**

```bash
dbkeygen foreign tablename foreigntablename columnname [columnname2 ...]
```

## How Its Coded

- Uses [picocli](https://picocli.info/) for argument processing
- Uses Hibernate 5.6 for hashing code
- Build with JDK 21
