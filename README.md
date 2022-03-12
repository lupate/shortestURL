# shortestURL
This project demonstrates the use of Spring Boot and Redis to build a URL shortener api. 

setup Pre-requisite
 - Java 1.8 or above
 - IntelliJ or Eclipse IDE
 - Docker

Setup Redis using Docker
 - docker pull redis
 - docker image ls | grep redis
 - docker run -d --name=redis -p 6379:6379 redis
 - docker container ls

using Redis-cli
 - redis-cli

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
