# Gradle dotnet plugins [![Build Status](https://travis-ci.org/bamboo/gradle-dotnet-plugins.png)](https://travis-ci.org/bamboo/gradle-dotnet-plugins)

[Gradle](http://www.gradle.org/) plugins for building, testing and publishing .NET, Mono and [Unity](http://unity3d.com/) components and applications.

## Status

The plugins can already build, test, publish and manage dependencies of C#, [Boo](http://boo.codehaus.org/) and [UnityScript](http://github.com/bamboo/unityscript) components
using the Mono runtime shipped with Unity. There's also good support for consuming [NuGet](http://nuget.org/) packages as [ivy](http://ant.apache.org/ivy) modules.

Next on the roadmap is support for using MS.NET on Windows and system Mono everywhere to ditch the Unity requirement for common usage.

Would you like to help? Join the [mailing list](https://groups.google.com/forum/?fromgroups=#!forum/gradle-dotnet-plugins).

## Requirements

1. Java
2. Unity 4.2 (the free version will do)

## Rationale

Breaking system functionality into smaller units that can be evolved and deployed independently of each other makes integration simpler
because it can happen in small and localized steps. Divide and conquer.


## The Anatomy of a Modular Application

User visible functionality is grouped and delivered as coarse grained units called *bundles*. 

Bundles are versioned groupings of *components*. 

Components are versioned sets of files (such as a dotnet assembly and its xml documentation file for example) stored in *repositories*.

Repositories are directories of components, local or remote.

Components might declare dependencies on (versions of) other components.

The version number of a component is incremented every time a new version is published in accordance with [semantic versioning rules](http://semver.org/).

Components can and should be published independently of each other.

## License

[The MIT license.](LICENSE)

## Contributors

See: https://github.com/bamboo/gradle-dotnet-plugins/graphs/contributors
