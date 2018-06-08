# Object Language Recognition system

This project is conceived as an implementation of language recognition using 
object-oriented approach. The creation of objects is similar to chemical 
processes in the interaction of more simple objects.

The recognized text is considered as a serialized representation of an object as a string, i.e. a
 string of characters, in a certain language. This string interacts with the language. As a 
 result of this interaction, a chain of tokens appears. Tokens interact with each other and 
 generate the object, which is the result of recognition. 

## Sentence
the Sentence class is a wrapper for recognized text.

Using the created language is simple. If we want to recognize text written on **SomeLanguage** and 
want to get an object of type **ResultType**, we need to do the following:

```java
...
ResultType resultObject=new Sentence<Language,ResultType>("the text for recognition", new SomeLanguage()).toObject();
...
```

## Language creation

The interaction between two objects is implemented as a call to the **concat** method of one of them.
 A second object is passed to the method as an argument. The output is the result of interaction.
 
 Creating a result object is similar to using the **Builder** pattern (only instead of the 
 *build* method using *concat*), managed by the input character stream.
 
Creating a parser for a language is an implementation of the following interfaces:

## Lang
Recognized language. The object that implements this interface is able to interact with 
unrecognized text, carrying out the primary recognition process, i.e. the separation of 
the input stream of characters into tokens of the language.
This interface should also be implemented by other objects (e.g. tokens), if unrecognized text 
can get into interaction with them.
## Token
A language token is a predefined set of characters that is arranged sequentially and related to 
a given language. The token is recognized as a separate object.
## Tokens
The set of all tokens of the language. Required when implementing the **Lang** interface. There
  is a default implementation in the **TokenSet** class.

## Sense
All objects appearing in the recognition process must implement this interface. It is responsible
 for interactions between objects by default.

### Setup

Releases are published to sonatype.org and to maven central. You may download artefacts manually:

[Sonatype](https://oss.sonatype.org/content/groups/staging/su/izotov/ObjectLR/)

[Maven Central](http://repo1.maven.org/maven2/su/izotov/ObjectLR/)

or using build systems.

Maven:

```xml
<dependency>
  <groupId>su.izotov</groupId>
  <artifactId>ObjectLR</artifactId>
  <version>0.1</version>
</dependency>
```

Gradle:

```groovy
dependencies {
    compile 'su.izotov:ObjectLR:0.1'
}
```
 
## Example languages

will be published soon...

