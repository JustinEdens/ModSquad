# ModSquad
 Sharing network for modified cars.
 
### Home:
* User can go to the Add or Search fragments with the menu
### Add: 
* User can add Driver info
* User can add car year, make, and model
* User can select any engine modifications
* User can select any exhaust modifications
* User can submit file, that will get sent the the ModSquadAWS application which will parse the file and add the info to a DynamoDB database table
### Search:
* User can search year, make, and model of all inserted cars
* Query gets sent to ModSquadAWS application that retrieves result set of query
* Result is parsed and produces a list of CarProfile objects

# What I Learned
* Commmunication from an Android mobile application and a server program in the AWS Cloud
* Adding and Querying a DynamoDB database from a server program in Elastic Beanstalk
* Adding a server program to AWS Cloud
