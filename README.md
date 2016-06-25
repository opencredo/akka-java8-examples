# akka-java8-example

 
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


## License

Copyright (c) 2015 Open Credo Ltd, Licensed under MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.