# Perlin-Noise-Map-Generator

Uses Ken Perlin's algorithm, which is showed below.
<img width="497" alt="Screen Shot 2021-09-23 at 9 22 19 PM" src="https://user-images.githubusercontent.com/76085879/134617903-fb882f3a-54b2-4a08-aa09-0687ebad5c77.png">

Dark spots have less value, white spots have more. Using a simple equation to transform the above image into 0-256 ints, then using those ints as RGB values, we have something like this.
<img width="499" alt="Screen Shot 2021-09-23 at 9 14 39 PM" src="https://user-images.githubusercontent.com/76085879/134618024-05b8c9b0-4fcd-466f-8267-8d9e5e8ce4bb.png">

There are certain cutoffs to find - if below a certain value, it is ocean. If above, it is a mountain, etc. etc.

In a nutshell, Perlin noise is made by making a grid, where each grid intersection has a random unit vector. Then, you take the dot product of any given point and its corresponding four vectors. Afterwords, you linearly interpolate the dot products together, creating an image that looks random yet still natural.
