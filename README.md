# we-dont-need-another-di-framework
Sample Java project on how to create a working non-trivial testable app with proper structure and manual DI by just using regular Java 8+ language features.

The idea behind this repo is to show how to setup a non-trivial REST api with as few frameworks as possible.
It is very easy to always resort to using Spring Boot to create Java based applications, but my opinion is that it brings a lot of stuff that you dont really need.
Java as a language has evolved and the JDK now comes bundled with a very good http client, so lets see how easy it is to create a testable app without any frameworks at all.

The inspiration for this sample is the excellent blog post by Anders Sveen at https://medium.com/porterbuddy/rolling-your-own-dependency-injection-7045f8b64403
Although I'm also a big fan of Kotlin there are projects and teams where a "new" language does not fit, so this application shows how to replace Kotlin's optional named parameters with optional injected factories through overriding default interface methods.
