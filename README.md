# ZooManagementSystem
University coursework I built in Java on a zoo management system

A console-based Zoo Management System developed in Java as part of my university coursework.

The project shows core Object-Oriented Programming (OOP) principles I learnt by modelling different types of animals and providing a system for managing animals within a zoo.
Coursework Grade: 80%

Object-Oriented Programming

The project demonstrates several important Java and OOP concepts.

Encapsulation

Animal attributes such as name, age, colour and weight are stored as private fields and accessed through getters and setters.

Inheritance

Animal is an abstract superclass containing attributes and behaviour shared by all animals.

All these classes inherit from animal
Lion
Dolphin
Parrot
Each subclass also contains information specific to that animal.

Abstraction

The Animal class defines the abstract makeSound() method.

Each animal subclass provides its own implementation of this method, allowing animal-specific behaviour while maintaining a common structure.

Interfaces
Two interfaces are used to represent abilities that only certain animals have:
Flyable
fly()
checkWingHealth()
Swimmable
swim()
checkFinHealth()
Parrot implements Flyable, while Dolphin implements Swimmable.

Polymorphism
Animals are stored together using an Animal[] array.
The program can treat each object as an Animal while still executing behaviour belonging to its specific subclass. Interface-based polymorphism is also used during daily care to perform specialised actions for animals that can fly or swim.
