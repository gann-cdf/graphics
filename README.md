# Gann Graphics

A thin wrapper for AWT/Swing 2D graphics

[Click here for complete documentation](https://gann-cdf.github.io/graphics/)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.gannacademy.cdf/graphics/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.gannacademy.cdf/graphics)  [![Scrutinizer Code Quality](https://scrutinizer-ci.com/g/gann-cdf/graphics/badges/quality-score.png?b=master)](https://scrutinizer-ci.com/g/gann-cdf/graphics/?branch=master)

### Design Philosophy

This rramework was developed to support introductory computer science students in quickly developing graphics and animation-rich applications. I made several decisions as a reault of this purpose:

  - **Swing-based implementation.** This is basically an update to the [Williams College ObjectDraw framework](http://eventfuljava.cs.williams.edu/library.html) that is purely Swing-based, to facilitate an easier transition into Swing programming.

  - **Encapsulated stroke and fill.** A seemingly trivial detail, but I see this as a way to start to encourage students to think about data encapsulation. Rather than thinking of the various drawable components as a series of instructions (whose order matters) applied to the drawing panel, students are encouraged to think of stroke and fill as characteristics encapsulated in the shapes themselves.
  
  - **Tight connection to `java.awt.*`** Rather than provide additional convenience (and, perhaps consistency), the basic model of AWT geometric shapes wrapped in container objects is not concealed from students. This is to facilitate an eventual transition away from these "training wheels" -- but also to encourage them to _follow links_ to read documentation!
  
  - **Student-oriented documentation.** Much of the documentation that I have written is driven by questions asked by students in the past. In partcular, a visual description of how [Bézier curves](src/main/java/org/gannacademy/cdf/graphics/geom/CubicCurve.java) are calculated is meant to demystify an often confusing tool. In addition, I have used the framework itself to [generate the figures](src/main/java/org/gannacademy/cdf/graphics/javadoc/GenerateFigures.java) in the documentation itself, having observed student frustration over purely text-based documentation.
  
  - **No hidden main() method.** This has appeared to be particularly troubling to students, who are struggling to conceptualize the flow of control. I believe exposing the `main()` method is a neat parallel to requiring instantiation as well as declaration &mdash; or to requiring `.registerKeyListener(this)` as well as `implements KeyListener`. If it's really two steps, show both steps.