# Data Retrieval Service

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

Ensure that you run these commands from the project's root folder, where the Project Object Model (POM) file is located.

## Notes

This service is responsible for serving the client's filter and view requests. There are several different criterias for filtering applied.
