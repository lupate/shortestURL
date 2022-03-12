# shortestURL
This project demonstrates the use of Spring Boot and Redis to build a URL shortener api. 

Execution
java -jar short-url.jar

API Details

Create Short URL:
http://localhost:8085/api/v1/shorten

Request body:
{
    "url": "https://www.gmail.com"
}

Response body:
{
    "id": "94717296",
    "url": "https://www.gmail.com",
    "created": "2022-12-02T14:11:26.887"
}

Response codes:
HTTP Status	Description
200	successful operation
500	internal server error

Retrieve Original URL:
http://localhost:8085/api/v1/original/{id}

Response body:
{
    "id": "94717296",
    "url": "https://www.gmail.com",
    "created": "2022-12-02T14:11:26.887"
}

Response codes:
HTTP Status	Description
200	successful operation
404	not found
500	internal server error
