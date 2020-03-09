# graphattan
slightly unreasonable by most people's standards

## Overview
This project models the progress of a pedestrian through a grid of city streets, inspired by a series of conversations
about walking commutes in Manhattan. A shared dislike of waiting at intersections and desire to optimize these trips encouraged the participants to consider the following questions:
- What distinct patterns emerged over many trips?
- What rules governed the decision made to turn one way or another at an intersection?
- By observing many successive trips, could those rules be modified to consistently result in more optimal commutes?

### CityGraph
- Basic CityGraph model is described [here](https://github.com/erchiggins/graphattan/wiki/CityGraph).
- Simple, programmatically generated grid based on Midtown Manhattan is described [here](https://github.com/erchiggins/graphattan/wiki/MidtownGraphBuilder).

### Generation API
The Java project in this repository exposes an API which generates a JSON representation of a CityGraph DTO, with some nonessential (for basic traverals) information stripped away. It is presently hosted at `http://graphattan.us-west-1.elasticbeanstalk.com/graph` and queries should pass the starting and finishing street, avenue, and corner, e.g. 
``graphattan.us-west-1.elasticbeanstalk.com/graph?start_st=49&start_ave=1&start_corner=NE&finish_st=53&finish_ave=3&finish_corner=NE
``

### Visualization and Path Modeling
This being a bit of a Frankenproject, because Python lends itself more naturally to this sort of thing, parsing the generated CityGraphs and modeling/displaying traversals is implemented [here]() using networkx and matplotlib. For now, traffic light and pedestrian signal timings are ignored.

### Availability Functions
- TODO
