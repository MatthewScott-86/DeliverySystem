# City Deliver System

The city delivery system simulates a real world scenario wherein stores must connect 
with a pool of drivers to have their goods delivered. To accomplish this, drivers, running
on their own threads, are registered with a central distpatcher, stores push orders to the
same central dispatcher, which adds the new order to a list of available orders and notifies
the drivers that their is an available order. When drivers are free they request access to
the order list from the dispatcher and take an order. 

## Getting Started

### Prerequisites

Maven and the Java Development Kit will be necessary to compile, test, and run this program. 

### Testing

In the folder containing src folder and pom.xml file, run the command "mvn test"

### Generating jar file

In the folder containing src folder and pom.xml file, run the command "mvn package"

Note: This will run tests as well

## Running 

### Arguments

1. Number of Drivers (restricted to 30)
2. Number of Drivers with Freezers (restricted to number of drivers)
3. Number of Customers 
4. Rush hour or not (Boolean)
5. Todays Date

### Command Line Example

'mvn exec:java -Dexec.args="10 2 10 true 2017-08-25"

### Notes

All customers are born on the 25th day of months starting in January i.e. if there is one customer
their birthday is on January 25th, if there are two, their birthdays are January and Februrary 25th, etc

