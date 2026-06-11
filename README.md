# akka-java8-example

> [!NOTE]
> This repo has been archived and is no longer under development. OpenCredo is now part of [Trifork](https://github.com/trifork).

Sample code related to the blog post https://opencredo.com/improved-akka-java-8/

A few, very simple examples of using Akka Java 8 interface. Implementing a bar (customer-barista interaction).



## Running examples

```
mvn package exec:java -P simple
```
The simplest implementation using a simple `AbstractActor`

Not much interaction is expected. Press `Ctrl-C` to stop the application


```
mvn package exec:java -P become
```
A barista actor that _becomes_ ready when the coffee machine is warmed up.


```
mvn package exec:java -P future
```
Implementation using `CompletableFuture`
