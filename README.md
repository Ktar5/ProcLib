<h1 align="center"> ProcLib </h1> <br>
<p align="center">
  <a href="https://github.com/Ktar5/ProcLib">
    <img alt="Header" title="Header" src="https://i.imgur.com/79Za4X7.gif">
  </a>
</p>
<div align="center">
  <strong>A procedural content generation library for use in Java with support for LibGDX</strong>
</div>

<div align="center">
  <!-- Github Release -->
  <a href="https://github.com/Ktar5/ProcLib">
    <img src="https://img.shields.io/github/release/Ktar5/ProcLib.svg?style=flat-square"
      alt="Github Release" />
  </a>
  <!-- Git Stars -->
  <a href="https://github.com/Ktar5/ProcLib/stargazers">
    <img src="https://img.shields.io/github/stars/Ktar5/ProcLib.svg?style=flat-square"
      alt="Git Stars" />
  </a>
  <!-- Build Status -->
  <a href="https://github.com/Ktar5/ProcLib">
    <img src="https://img.shields.io/travis/Ktar5/ProcLib.svg?style=flat-square"
      alt="Travis Build Status" />
  </a>
  <!-- PR Welcome -->
  <a href="http://makeapullrequest.com">
    <img src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square"
      alt="PR" />
  </a>
  <!-- License -->
  <a href="https://github.com/Ktar5/ProcLib/blob/master/License.txt">
    <img src="https://img.shields.io/github/license/Ktar5/ProcLib.svg?style=flat-square"
      alt="License" />
  </a>
</div>

This library was created with inspiration from @Durango. Currently, this toolkit only has a small amount of
functions that relate to 2D int-array manipulation and rendering. However, in the future I hope to grow this
project to support a significant amount of random/procedural generation algorithms that can be implemented as-is
in your project or be used as a sample for implementation and execution of these algorithms.

## Examples
>Todo

Example stretching
```java
    ProceduralData procArray = new ProceduralData(20, 20, "stretch");
    Pair[] pairs = Neighborhood.DONUT.getPairs(5);
    for (Pair pair : pairs) {
        procArray.set(pair.x + 10, pair.y + 10, 1);
    }
    procArray = Resize.stretch(procArray, 1.0f, 1.5f);
    print(procArray, PrintMethod.SYMBOLS);
```

Example detecting and isolating blobs with `size > 3`
```java
    ProceduralData procArray = new ProceduralData(10, 10, "blobs");
    procArray.noise(.45f, 1);
    procArray.findBlobs(new BlobData()
            .filter(BlobData.BlobFilter.ABOVE, 3)
            .overwriteGiven(true)
            .render(BlobData.BlobRender.ONE));
    print(procArray, PrintMethod.SYMBOLS);
```

_For more examples and usage, please refer to the [Wiki][wiki]._

## Installation
If running Gradle, simple put the following into your `build.gradle`
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
...
dependencies {
    compile 'com.github.Ktar5:ProcLib:master-SNAPSHOT'
}
```

Or if you are running Maven, put the following into your `pom.xml`
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
...
<dependency>
    <groupId>com.github.Ktar5</groupId>
    <artifactId>ProcLib</artifactId>
    <version>master-SNAPSHOT</version>
</dependency>
```

## Release History
- None yet

## Contributing
>Todo
1. Fork it (<https://github.com/yourname/yourproject/fork>)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

<!-- Markdown link & img dfn's -->
[npm-image]: https://img.shields.io/npm/v/datadog-metrics.svg?style=flat-square
[npm-url]: https://npmjs.org/package/datadog-metrics
[npm-downloads]: https://img.shields.io/npm/dm/datadog-metrics.svg?style=flat-square
[travis-image]: https://img.shields.io/travis/dbader/node-datadog-metrics/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/dbader/node-datadog-metrics
[wiki]: https://github.com/yourname/yourproject/wiki


<br></br>
Carter Gale – [@Ktar5](https://twitter.com/ktar5) – ktarfive@gmail.com – [Github](https://github.com/ktar5/)
