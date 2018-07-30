# Smart-Dots
Smart Dots is an application that uses a [Genetic Algorithm](https://en.wikipedia.org/wiki/Genetic_algorithm) to find a path to a target hindered with obstacles.
## Working
The Genetic Algorithm at the end of every simulation produces a new Generation of Dots from the best Dots of the previous generation. Dots are rated according to factors such as their distance from the target, the number of steps they took to reach, etc. Now the new Generation before deployement is mutated i.e their DNA is randomly changed. This introduces new possible solutions. And the cycle repeats. After a while good mutations get propagated in the generations and overall fitness increases. Given long enough time the algorithm arrives at the shortest path to the target.
