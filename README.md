# PlantsVsZombies

## Introduction
This project was developed while I was studying the course Programming Technology (OOP) at Complutense University of Madrid. I developed it along with my American teammate Brandon Jia

## Description
The project simulated the famous game Plants vs. Zombies on the console. It models the commands and the game items (zombies, plants, sun coins, etc) as objects
The project was developed with the OOP principles in mind
- Encapsulation: each object manages its own data. An event might affect an object's data, but the decision on how to modify the data to respond to that event is eventually made by the object itself
- Abstraction: the implemetation of a feature is hidden from the callers. One method does one thing
- Inheritance: there are two inheritance hierarchies. The first one concerns the treatment of the commands introduced by the user at the keyboard and the second one is used to to organise the game objects which represent the different creatures appearing in the game. This helps to avoid repetitive code and enable the use of a single data structure
- Polymorphism: popularly used in this project. The main use case of polymorphism is to use the parent data type where the information about which specific children type is used does not matter. This makes the code very flexible and robust. For example, adding or removing a command or a new game object would be very easy

The project also implements Factory Pattern and Command Pattern, two of the 23 design patterns in the book "Design patterns Elements of Reusable Object Oriented Software". It also uses exections and I/O mechanisms

## Demonstrated knowledge and skills
- OOP
- Java
- Exceptions
- I/O in Java

