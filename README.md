# Smart-Dots
Smart Dots is an application that uses a [Genetic Algorithm](https://en.wikipedia.org/wiki/Genetic_algorithm) to find a path to a target hindered with obstacles.

## Principle
A simulation is run for the Dots to try and reach the target. The Genetic Algorithm at the end of simulation produces a new Generation of Dots from the best Dots of the previous generation. Dots are rated according to factors such as their distance from the target, the number of steps they took to reach, etc. Now the new Generation before deployement is mutated i.e their DNA is randomly changed. This introduces new possible solutions. And the cycle repeats. After a while good mutations get propagated in the generations and overall fitness increases. Given long enough time the algorithm arrives at the shortest path to the target.

## Specifics
The target is Yellow colored. The best Dot from the previous generation is highlighted in red. After the path is discovered, the algorithm optimizes for the quickest path by only considering Dots better than the previous generation.

## Here are some Screenshots:

![3](https://user-images.githubusercontent.com/34769156/43474411-6ceda20a-9510-11e8-8677-ebfdcfe5a3fb.png)

![32_a](https://user-images.githubusercontent.com/34769156/43474754-617d55ea-9511-11e8-9479-816f83762cd3.png)

![32_b](https://user-images.githubusercontent.com/34769156/43412582-56840c72-944b-11e8-855d-83fdfb7cdc10.png)
