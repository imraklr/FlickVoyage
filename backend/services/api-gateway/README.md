# API Gateway

## Prerequisites

Ensure that your system has Java 21 installed. You can verify the Java installation by executing the following command in your terminal:

```java --version```

If no errors are displayed, proceed to check if Maven is installed. If Maven is installed, run the following command:

```mvn clean spring-boot:run```

If Maven is not installed, you have two options:

* Install Maven and then run the command mentioned above.
* Run the following command based on your operating system:
  * For Unix-based systems:</br>
        ```./mvnw clean spring-boot:run```
  * For Windows:</br>
        ```./mvnw.cmd clean spring-boot:run``` (On Powershell)</br>
        ```mvnw.cmd clean spring-boot:run``` (On Command Prompt)

Ensure that you run these commands from the root folder, where the Project Object Model (POM) file is located.

## Overview of the API Gateway

The API Gateway plays a crucial role in establishing a unified pathway to interact with various services within the microservices architecture. Serving as a central hub, this service facilitates seamless access to the essential services integral to the success of the project. It acts as a conduit, simplifying communication and routing requests efficiently across the interconnected microservices.
