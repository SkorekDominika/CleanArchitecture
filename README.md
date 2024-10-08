# Incremental Study Loader with Clean Architecture

## Table of Contents

- [Overview](#overview)
- [Clean Architecture Principles](#clean-architecture-principles)
  - [Domain Layer](#domain-layer)
  - [Application Layer](#application-layer)
  - [Infrastructure Layer](#infrastructure-layer)
- [Incremental Loading Solution](#incremental-loading-solution)
  - [Key Concepts](#key-concepts)
  - [Batch Loading](#batch-loading)
  - [Pagination and Performance Optimization](#pagination-and-performance-optimization)
- [Tech Stack](#tech-stack)
- [Testing](#testing)
- [Contributing](#contributing)
- [License](#license)

## Overview

This repository contains an implementation of **Incremental Study Loading** following **Clean Architecture** principles.
The system is designed to load large sets of study data incrementally, while maintaining a clean, scalable, and easily
maintainable code structure.

## Clean Architecture Principles

The project is structured following **Clean Architecture**, which promotes separation of concerns, testability, and
scalability. The system is organized into three layers:

### Domain Layer

The **Domain Layer** contains the core business logic and is independent of external dependencies. It includes:

- **Entities**: Core business models that represent the main data objects.

### Application Layer

The **Application Layer** coordinates between the Domain and Infrastructure layers and facilitates the flow of data. It
contains:

- **Services**: Application-specific logic, facilitating use case execution.
- **DTOs**: Data Transfer Objects to manage data passing between layers.

### Infrastructure Layer

The **Infrastructure Layer** handles communication with external systems (e.g., databases, APIs). This layer includes:

- **Repositories**: Implementations of data access interfaces.

## Incremental Loading Solution

### Key Concepts

**Incremental Loading** enables the system to load data in small chunks consisting of single dicom files.
This allows for faster presentation of existing data.

### Performance Optimization

Multiple views created for the same study context present the same cached study

- **Caffeine Cache**: Study data is cached using the **Caffeine** library to improve performance and reduce repeated
  API/database calls.

## Tech Stack

The following technologies and tools are used in this project:

- **Java**: Core programming language
- **Guice**: Dependency injection framework for managing object lifecycles and dependencies
- **MBassador**: Lightweight event bus for asynchronous and synchronous event dispatching
- **Caffeine**: High-performance caching library for Java
- **JUnit** & **archunit**: Units testing core logic and architecture
