# flooringOOP

The goal of this project is to create an application that can read and write flooring orders for SWG Corp. 
Topics covered in this section:

N-tier/MVC architecture, including the use of a service layer
Interfaces
Spring dependency injection
Spring aspect-oriented programming
Unit testing

An order consists of an order number, customer name, state, tax rate, product type, area, cost per square foot, labor cost per square foot, material cost, labor cost, tax, and total.

Taxes and product type information can be found in the Data/Taxes.txt and Data/Products.txt files. The customer state and product type entered by a user must match items from these files.

Orders_06012013.txt is a sample row of data for one order.

Architectural Component
Use N-tier development and MVC principles in structuring code to handle products, taxes, and orders appropriately. 

Layers:
The model package only contain classes that have data members (properties).
The dao package contains classes that are responsible for persisting data.
The controller package contains classes that orchestrate the program.
The view package contains classes that interact with the user.
The service package contains the service layer components.  
The UserIO class (along with the view component) will handle all IO for the user.
Build this application following the process outlined in the Agile Approach Checklist for Console Applications
User Stories
The UI should start with a menu to prompt the user for what they would like to do:

    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *

    *  <<Flooring Program>>

    * 1. Display Orders

    * 2. Add an Order

    * 3. Edit an Order

    * 4. Remove an Order

    * 5. Save Current Work

    * 6. Quit

    *

    * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
