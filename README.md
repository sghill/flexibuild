Flexibuild
==========

Flexibuild is a tool to help build Java projects owned by someone else.

Goal
----

Assess and improve the maintenance of Java projects, such as Jenkins plugins.

Assessing how maintained a project is involves answering a series of questions:
* How is this project built?
* Can I run this project's build tool?
* Can I successfully package artifacts in this project?
* Can I successfully run this project's tests?

Flexibuild provides a unified interface over these steps, without needing to know if
the project uses Gradle or Maven.

Examples
--------

```
% flexibuild smoke-test # runs `./gradlew help`    or `mvn help:effective-pom`
% flexibuild compile    # runs `./gradlew classes` or `mvn compile`
% flexibuild package    # runs `./gradlew jpi`     or `mvn hpi:hpi`
% flexibuild test       # runs `./gradlew check`   or `mvn verify`
```

Motivation
----------

Our Jenkins controllers use over 250 open source plugins.
Many of these plugins are maintained with great care by the community.

Some are unmaintained.
Reacting in realtime to [Log4Shell][log4shell], ["tables to divs"][tables], and [requiring Java 11][java11] have all been significant projects.
There is great value in:

1. knowing how well maintained the code we rely on is
2. improving the maintenance of code we rely on

[log4shell]: https://en.wikipedia.org/wiki/Log4Shell
[tables]: https://www.jenkins.io/changelog-stable/#v2.277.1
[java11]: https://www.jenkins.io/changelog-stable/#v2.361.1

