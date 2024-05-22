# SIS Backend

## Tools and Libraries Used

The basic Rest project has been generated from [https://start.spring.io/](https://start.spring.io/).  I used this as the pom is generated with the correct compatible versions of the libraries added.  I selected the last stable version as the core spring-boot.

### Tools

I have used Postman for the majority of the manual testing of the application.  You can also use any web browser for the get operations (You will notice that I had to make a change to the code to allow this for operations using the request-header).

### Dependencies

- SpringWeb  
	Included for basic RESTFul and Spring MVC functionality.
- Validation  
	Included for Bean annotation validations such as NotEmpty
- Actuator  
	Not used specifically for this project as I'm not actually monitoring that the service is running 
but allows monitoring of the application's health and I tend to add this in by default.

### Additional Libraries

- JUnit5  
	In my previous projects I've used JUnit4, however, I am currently bringing my skills up to date so I decided to use the latest library instead
- Jackson-core  
	Used for sending the response in XML when expected for the search functions
- Log4J  
	Used for logging, again I've used one of the more recent libraries

## Installation

Download the project.  From the root directory (containing the pom file) and run the following:
> mvn spring-boot:run

The service should be available on port 8080 so please make sure that port is not already in use when you run it 

## Usage

You can use the following to test the basics supplied in the service using Postman.  For convenience 
I've returned user friendly messages to show that the service has worked or if an error has occured. 
I've stopped unexpected runtime exceptions from returning a detailed message of the error so that 
the calling program isn''t shown the details of the underlying code but allows you to look for the problem 
in the logs.  

Note that the feedback type needs to be entered in uppercase.  I did enter a converter which should have taken care of that but it hasn't worked.  I think this could be due to a change in the way the latest libraries work 
or possibly there is a bug introduced as I did try several ways to force it but I've decided now to take that
away from this piece of work and will look at it after submitting this.

Function: Add a retrospective  
Request type: **Post**  
URL: [http://localhost:8080/v1/retrospective](http://localhost:8080/v1/retrospective)  
Body:  
`	{
	    "name": "Postman2",
	    "summary": "Postman Test Summary",
	    "date": "01/01/2024",
	    "participants": ["Paul", "Kevin"],
	    "feedbackItems": []
	}`

Function: Add some feedback.  Note that the feedback type has to be in uppercase.  
Request type: **Post**  
URL: [http://localhost:8080/v1/retrospective/feedback/Postman2](http://localhost:8080/v1/retrospective/feedback/Postman2)  
Body:  
`	{
	    "name": "Paul",
	    "body": "Sprint objective met",
	    "feedbackType": "POSITIVE"
	}`

Function: Get the first page of a list of retrospectives with a maximum of 10 items per page  
Request type: **Get**  
URL: [http://localhost:8080/v1/retrospective/1/10](http://localhost:8080/v1/retrospective/1/10)  

Function: Get the first page of a list of retrospectives with the specified date with a maximum of 10 items per page  
Request type: **Get**  
URL: [http://localhost:8080/v1/retrospective/datesearch/1/10?retrospectiveDate=1/1/2024](http://localhost:8080/v1/retrospective/datesearch/1/10?retrospectiveDate=1/1/2024)  

Function: Update some feedback.  Note that the feedback type has to be in uppercase. Also note that if the name is supplied it must match the name already associated to the feedback  
Request type: **Put**  
URL: [http://localhost:8080/v1/retrospective/feedback/Postman2](http://localhost:8080/v1/retrospective/feedback/Postman2)  
Body:  
`	{
	    "name": "Paul",
	    "body": "Sprint objective met",
	    "feedbackType": "NEGATIVE"
	}`
