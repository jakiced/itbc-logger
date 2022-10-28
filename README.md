# Logger
---
## Tracking application for client made logs

This is an application that enables registered users to save their vital information, which they can later retrieve by using various filters.

## Made with : 
<!-- <table width="320px">
 <td width="80px" align="center">
            <span><strong>Java</strong></span><br>
            <img height="32" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg">
            </td>
</table> -->

<code><img width="10%" src="https://www.vectorlogo.zone/logos/java/java-ar21.svg"></code> version 19
<code><img width="10%" src="https://www.vectorlogo.zone/logos/springio/springio-ar21.svg"></code> version 2.7.5
<!-- https://www.vectorlogo.zone/logos/java/java-ar21.svg
https://www.vectorlogo.zone/logos/springio/springio-ar21.svg -->

## User
1. User is able to register by providing
   * username, email, password
   
2. User can fetch his "key" using username, email, password

4. User can create a log by entering 
   * message
   * log type
   * date the log was created
 
5. User is able to search the logs by 
   * time 
   * parts of text present in the log message
   * type of the log (ERROR, WARNING, INFO) 
   * combining any of the above 


## Admin 
Admin has privileges to 
   * View all users
   * View number of logs per user
   * Change the user's password if requested

---
# Endpoints

## Client

1. Register
    - HTTP Method: `POST`
    - Endpoint URL: `/api/clients/register`
    - Request body:
        ```json
        {
            "username": "string",
            "password": "string",
            "email": "string"
        }
        ```
    - Responses:
        - 201 - Registered
        - 400 - Bad Request
            - email must be valid
            - username at least 3 characters
            - password at least 8 characters and one letter and one number
        - 409 - Conflict
            - username already exists
            - email already exists

2. Login 
    - HTTP Method: `POST`
    - Endpoint URL: `/api/clients/login`
    - Request body:
        ```json
        {
            "account": "string", // email or username
            "password": "string"
        }
        ```
    - Responses:
        - 200 - OK
            ```json
            {
                "token": "string" // uuid* || JWT || username
            }
            ```
        - 400 - Bad Request
            - Email/Username or password incorrect

3. Create log
    - HTTP Method: `POST`
    - Endpoint URL: `/api/logs/create`
    - Request body:
        ```json
        {
            "message": "string",
            "logType": 0
        }
        ```
    - Request headers:
        - `Authorization` - token
    - Responses:
        - 201 - Created
        - 400 - Bad Request
            - Incorrect logType
        - 401 - Unauthorized
            - Incorrect token
        - 413 - Payload too large
            - Message should be less than 1024

4. Search logs
    - HTTP Method: `GET`
    - Endpoint URL: `/api/logs/search`
    - Request params:
        - `dateFrom` - date
        - `dateTo` - date
        - `message` - string
        - `logType` - int
    - Request headers:
        - `Authorization` - token
    - Responses:
        - 200 - OK
            ```json
            [
              {
                "message": "string",
                "logType": 0,
                "createdDate": "date"
              }  
            ]
            ```
        - 400 - Bad request
            - Invalid dates
            - Invalid logType
        - 401 - Unauthorized
            - Incorrect token

<div style="page-break-after: always;"></div>

## Admin

1. Get all clients
    - HTTP Method: `GET`
    - Endpoint URL: `/api/clients`
    - Request headers:
        - `Authorization` - token (Admin token)
    - Responses:
        - 200 - OK
            ```json
            [
              {
                "id": "uuid",
                "username": "string",
                "email": "string",
                "logCount": 0
              }  
            ]
            ```
        - 401 - Unauthorized
            - Correct token, but not admin
        - 403 - Forbidden
            - Incorrect token

2. Change client password
    - HTTP Method: `PATCH`
    - Endpoint URL: `/api/clients/{clientId}/reset-password`
    - Request body:
        ```json
        {
            "password": "string"
        }
        ```
    - Request headers:
        - `Authorization` - token (Admin token)
    - Responses:
        - 204 - No content
        - 401 - Unauthorized
            - Correct token, but not admin
        - 403 - Forbidden
            - Incorrect token
