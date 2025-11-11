---
title: "Nulls: Are we still losing millions of dollars with Java and Kotlin?"
description: An overview of nulls, NullPointerExceptions, and nullability in Java and Kotlin.
date: Created
tags: java,kotlin
---

For decades, the [NullPointerException](https://docs.oracle.com/javase/8/docs/api/java/lang/NullPointerException.html) in [Java](https://www.java.com/en/) has been known as “the million (or billion) dollar mistake”. Java first laucnhed in the mid-1990s and it included significant improvements to memory management and object-oriented features that led to its strong adoption. Unfortunately, these improvements did not include addressing null pointers. While Java has made great leaps into keeping up with modern language trends, we continue to be plagued by NPEs.

In this post I will explain what NullPointerExceptions (NPEs) are in Java, how Kotlin and Java have tried to address the issue, and how Java’s Project Valhalla proposes another step towards addressing them.

## Nulls and null pointers

What is `null`?

Computer memory, specifically [RAM](https://en.wikipedia.org/wiki/Random-access_memory) is addressed. Each memory location can be referred to by its address, usually called a pointer or reference. Blocks of information representing application objects are stored in a memory block starting at the object's pointer. Java creates and maintains these pointers whenever you create objects. You can also tell Java that you no longer need the object by setting it to null - indicating there isn't an address anymore because there isn't an object in use anymore.

```java
Person person = new Person(); // person points to the location of the object in memory
person = null; // person no longer points to anything
```

In this code snippet, an object of type `Person` is created and the pointer to that object is stored in the variable `person` in the first line. The second line sets the `person` variable to `null` indicating that there is no relevant `Person` object because that variable now points nowhere. It doesn’t mean the object is removed from memory, just that we don’t have the address anymore. Eventually Java's garbage collector will identify unreferenced objects and remove them from memory.

When are `null`s used?

`null` can be used in different scenarios but in some cases, they are used to indicate the absence of something. For example:

```java
private Person findPersonByName(String name) {
    if (database.contains(name)) {
        return database.get(name);
    } else {
        return null;
    }
}
```

In this code, `null` is communicating that the name provided doesn't exist in the database.

Let’s look at a bit more code:

```java
Person person = new Person();
System.out.println(person.toString();

person = null;
System.out.println(person.toString();
```

Whenever you call a method on `person`, Java will execute the method on the object located where the variable `person` points to in memory. This works for the first print statement before the `person` is set to `null`. But what about the second case? What happens when you try to fetch data from memory when you don’t have an address?

The answer: NullPointerExceptions.

## Why are NullPointerExceptions bad?

NullPointerExceptions can happen any time you’re handling an object without checking if it's `null` first. Failure to do so may result in NPEs that stop the running program in whatever state it’s in, potentially corrupting data or leaving the application in a bad state. Java is an *object-oriented* programming language. There are objects everywhere, all the time, and so are NPEs.

One thing to note is that a NullPointerException is a type of RuntimeException or *unchecked* exception. In Java, you must declare that your method throws an exception if you have a `throw` statement in your method - but only if it's a *checked* exception (hence the name, Java checks for it). Since NPEs are *unchecked*, this means it is not declared in the method signature and the compiler won't check for correct handling of them.

There are ways to apply safeguards to prevent NPEs but since NPEs can happen in any object interaction, it’s time-consuming and verbose to handle these everywhere. Here's what that looks like:

```java
private void printPerson(Person person) {
    if (person != null) {
        System.out.println(person.toString());
    }
}
```

This doesn't seem too bad except in complex code, it can be hard to make sure all possible object `null` combinations are checked and you end up with. It is also hard to unit test this complex code, leading to hard-to-detect NPEs.

## Nullability

Nullability refers to describing something as "can be `null`". Some languages can specify nullability at compile time or runtime to describe that a variable can or cannot be `null`.

### Java

Java doesn’t distinguish between what can be `null` or what can’t be `null`. Outside of primitive types, anything can be `null`. This is one of the main reasons why it is hard to avoid NPEs.

Over the years, there have been attempts to help manage nullability in Java and I will go over Project Lombok and Java 8's `Optional`.

#### Project Lombok

[Project Lombok](https://projectlombok.org/) is a Java library to make writing Java more pleasant by taking care of common patterns with annotations. One of those cases is Java `null` checking. Here’s the vanilla Java code if you want to protect against NPEs:

```java
public void printPersonName(Person person) {
	if (person == null) {
		// Cannot be null
		throw new IllegalArgumentException("Person is null when expected to be not null")
	}
	System.out.println(person.name);
}
```

Here’s how Lombok saves some work:

```java
public void printPersonName(@NonNull Person person) {
  System.out.println(person.name);
}
```

By default, Lombok’s [@NonNull](https://projectlombok.org/features/NonNull) will rethrow an NPE but it can be configured to throw IllegalArgumentExceptions instead.

So that’s an improvement: first, the null check is written automatically just by adding the annotation and second, this code communicates that it expects non-null input. However, this is still a runtime check and you'll need to wait for your code to be deployed to find out if you missed any annotations.

#### Java Optional

Java added the [Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html) class in Java 8 as a way to help with NPEs. Optionals are a way to communicate that a variable may or may not be present.

```java
Optional<String> myString = Optional.of("Hello"); // Has value "Hello"
Optional<String> myMissingString = Optional.empty(); // Value is not present

if (myString.isPresent()) { // Will print
  System.out.println(myString.get());
}

if (myMissingString.isPresent()) { // Will not print
  System.out.println(myMissingString.get()):
}

// Problematic code
Optional<String> myMisusedOptional = null; // Asking for an NPE
```

Much like Lombok’s annotation, Java Optionals are able to communicate “this could be null” and it forces code writers to resolve the Optional value before the value can be used. Unfortunately, you can still set the value of an Optional to `null` and get an NPE, putting us back at square 1.

## Nullability in Kotlin: The answer to null pointer exceptions?

About 15 years after Java, [Kotlin](https://kotlinlang.org/) came into the picture as a JVM (Java Virtual Machine) language. Kotlin and Java both run on the JVM, though Kotlin is not limited to running in the JVM. I will stay focused on JVM Kotlin for this post and, in that context, Java and Kotlin can be interchanged to produce Java bytecode that runs on the JVM.

Kotlin supports declaring a variable as nullable or non-nullable. What’s even better is that Kotlin is non-nullable by default and this is a compile time check. Kotlin does this by requiring a `?` at the end of a declaration. When code is written well, this means you can’t build code without handling the `null` possibility:

```java
var nonNullName: String = "Jim" // Non-nullable
println(nonNullName.length) // All good
nonNullName = null // results in a compile time error

var nullableName: String? = "Jane" // Nullable
println(nullableName.length) // Does not compile because the null case must be handled
println(nullableName?.length) // Does compile due to use of ? to handle the null case
nullableName = null // allowed
```

Now we’ve gotten to the main point I want to make in this post:

> If you’re using Kotlin nullability, you can avoid most* accidental NullPointerExceptions and keep your billion dollars.

*And*, despite this critical language feature, **developers still choose to opt-in to NPEs**.

*The asterisk here comes back to JVM Kotlin and Java interoperability. If Kotlin code is calling Java code, the compiler doesn’t have a way to know if the Java parameters or return types are nullable or not and you can get unexpected NPEs. You can still use `?` on Java properties or return types to have defensive programming but the compiler will not remind you to do so.

Back to losing million in Kotlin: how and why are developers choosing to expose their software to NPEs when it’s (mostly) avoidable?

Kotlin supports “force not null” / `!!` if you want to skip null safety checks:

```java
var name: String? = null
name.length // Does not compile because the null case must be handled
name?.length // Does compile
name!!.length // Throws an NPE
```

Since developers have to opt-in to forcing something to be not null, why are they doing this? Let's look at this code:

```java
val postalCode: String? = person?.let { p ->
  p.address?.let { a ->
    a.postalCode?.let { pc ->
	    pc
    } ?: run { 
      println("PostalCode was null, try zipCode")
      a.zipCode?.let { zc ->
	      zc
      } ?: run { println("ZipCode was null")}
     }
  } ?: run { println("Address was null") }
} ?: run { println("Person was null") }

// OR with some possible NPEs

val postalCode: String? = person!!.address!!.postalCode ?: person!!.address!!.zipCode
```

Managing all the possible `null` scenarios can be tedious, messy, and overall annoying. Developers get fed up and just go back to the Java world where they’re hoping null values don’t actually show up in production to avoid this mess of `?.let` blocks.

Surely, we can minimize use of nullable variables and properties though, can't we? Unfortunately, there are cases where you can end up with a million levels deep of `?.let`:

1. You have nullable fields by design. Ex. A person’s address may not be specified and you have to handle nullability of all child fields
2. You’re interoperating with Java code where you have to check everything for null all the time
3. You’re using a serialization/deserialization library that requires everything to be nullable

The rule that I follow here: Never use `!!` in production code, even if you're sick of those `?.let` blocks.

Test code is an exception where you can reasonably use `!!` . If you do end up with an NPE when running your tests, that is catching a bug on its own.

## Another attempt at Nullability in Java: Project Valhalla

I will wrap up by going back to Java and talking about Project Valhalla. [Project Valhalla](https://openjdk.org/projects/valhalla/) is an in-development project in Java that proposes value types and some nullability support.

### Value Objects

Value  objects or classes from Valhalla are meant to support primitive-like objects where the value of the object is assigned once on creation and remains unchanged. Java considers two value objects as the same if the values of their fields are the same. Their declaration is similar with the note that primitive properties on value objects are final in order to support Java identifying they are equivalent by the primitive properties.

```java
// Plain old Java objects
public class Coordinate {
  final private int x;
  final private int y;

  public Coordinate(int x, int y) { ... }
}

// Value objects 
value class Coordinate {
  private int x; // these are implicitly final
  private int y;

  public Coordinate(int x, int y) { ... }
}

```

### Valhalla Nullability

Back to NPEs, Valhalla also proposes support to indicate whether a value class variable can be `null` or not. This will be a compile time error, much like with Kotlin’s nullability. Good progress for Java!

Below is an example of the proposed syntax using the previous Coordinate example:

```java
public static boolean movePawn(Coordinate! start, Coordinate! end) {
  ...
}

movePawn(null, new Coordinate(2, 3)); // Compile time error
```

Find out more about this JEP here: [https://openjdk.org/jeps/8316779](https://openjdk.org/jeps/8316779)

One last thing about Valhalla - it also includes a proposal to extend this to other objects using ! and ? except this would be another runtime check instead of compile time: [https://openjdk.org/jeps/8303099](https://openjdk.org/jeps/8303099)

## Kotlin is Great and Java isn't Dead

Kotlin nullability support is one of my favorite Kotlin language features. As a Java and Kotlin developer, I can appreciate what a difference it makes to prevent NPEs at compile time. Java has difficulty overcoming this costly issue; however, Java is not dead. Java continues to evolve towards writing more resilient and higher quality code to keep up with the other modern programming languages.