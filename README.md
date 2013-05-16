# Gradle dotnet plugins

[Gradle](http://www.gradle.org/) plugins for building, testing and publishing .NET, Mono and [Unity](http://unity3d.com/) applications.

## Rationale

Breaking system functionality into smaller units
that can be evolved and deployed independently of each other makes integration simpler
because it can happen in small and localized steps. Divide and conquer.


## In The Abstract

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
