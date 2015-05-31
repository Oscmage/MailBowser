# MailBowser
Send beautiful emails with Markdown.

## Usage
To run the program, go to the project root (where pom.xml is located) and run the following commands:

`> mvn clean`

`> mvn package`

This creates a jar file in the target folder that contains all dependencies. This file can now be run with:

`> java -jar target/mailbowser-0.1-SNAPSHOT-jar-with-dependencies.jar`

## Persistent data
All data storage is contained in the following files:
* Accounts.ser
* Contacts.ser
* Tags.ser

These files are placed in the folder the application is run from. To reset the application to its original state, simply delete these files.

## Documentation
RAD, SDD and use cases can be found inside the docs folder. 
Meetings in pdf format can be found inside meetings/PDF/.

