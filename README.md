# Shared Rent

This application will help you split rent and other apartment costs between all tenants. It will allow you to add extra payments so you as an administrator can charge extra money from chosen tenant. Application is being prepared to run on both mobile and desktop devices. 

## Getting Started

If you would like to just test this application live please visit http://nowak-dev.piekoszek.pl/. Please note that it is still under development and many functionalities may be missing. Latest stable feature branch should be deployed there.

However if you would like to deploy it on your own machine please follow instructions below. They will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* [Java 11 SDK](https://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [MongoDB](https://www.mongodb.com/download-center/community)
* [Maven](https://maven.apache.org/install.html)

### Installation

First of all install MongoDB server. Assuming that you left default settings during installation, app should be able to reach database right away. You may also need Lombok plugin for IntelliJ (or other IDE) to avoid typos on annotations used in the project. In order to build the project you will need Maven. To build executable *.jar* type "*mvn clean install*" or "*mvn spring-boot:run*" in your terminal. The last step is to run *.jar* file from *target* directory.

To access an app when it's running open your web browser and type:
http://localhost:8080/

## Built With

* [Spring Boot](https://spring.io/projects/spring-boot) - Web Framework
* [Maven](https://maven.apache.org/) - Dependency Management
* [Mongo](https://www.mongodb.com/) - Database

## Authors

* **[Jacek Nowak](https://www.linkedin.com/in/jacek-nowak-0b674111a/)** - *Development*
* **[Piotr Iwaniuk](https://www.linkedin.com/in/piotr-iwaniuk-179709140/)** - *Professional help and code validation*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
