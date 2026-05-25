# Notes

## <Put any thoughts about the exercise, your implementation, future improvements etc. in here.>

## Implementation idea
Provide separation between domain model, web layer, service layer and persistence layer

Email represents the email record
EmailStatus is an enum representing delivery state
EmailController handles HTTP
EmailService handles flow for sending an email
EmailRepository handles database access

I wanted to keep the controller light and have minimal logic directly in the controller to avoid coupling, so EmailController dlegates to EmailService. 

## Trade-offs 
In EmailService, there is some quite tight coupling between the external client (EmailClient) and the persistence (EmailRepository). This was done to keep the implementation simple since avoiding abstractions makes the entire flow easier to follow and understand. If I could revisit, I would perhaps add an abstraction for the email delivery and keep infrastructure and business logic more separate. Currently, changing the database might affect the API response.  

In EmailController, I added a method that maps controller to reponse (toReponse). In a real service, I would move it to its own mapper class since there would likely be more endpoints that need that same conversion. 

In SendEmailRequest, I only chose recipient, subject and body to keep a simple API, making it easier to understand and test. In a real system I would choose cc, attatchments and multiple recipients for example.


## If I had more time
As of now, only a simple HTTP status is returned for unauthoried access. If would add more detailed error responses if I had more time. 

With more time, in SendEmailRequest I would add required fields and some sort of format validation. 

I would add a test that makes sure that a user cannot read another user's email history. I would also add a test somehow tests a failed message using a valid token.  

