DESIGN DECISION:

The Task1 has userinfobook.xml as data source (created at CATALINA.HOME). Default home page index.html is provided with two forms.
form_1 call POST servlet for submitting the user preferences and persisting in data source.
form_2 call GET servlet to pull the records from data source based on criteria and search all records if no criteria is provided.   


ANT BUILD COMMANDS

1. ant deploy-all
2. ant clean
3. ant cleanall