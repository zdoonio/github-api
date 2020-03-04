# Github contributors in org

## Purpose
GitHub portal is centered around organizations and repositories. Each organization has
many repositories and each repository has many contributors. To use this demo you should do GET request on
endpoint that given the name of the organization will return a list of contributors sorted by the
number of contributions.

* respond to a GET request at port 9000 and address /org/{org_name}/contributors
* respond with JSON, which should be a list of contributors in the following format:
{ “name”: <contributor_login>, “contributions”: <no_of_contributions> }

## Configuration
In file application.conf user is allow to specify they creditinals to github app as following
```
github {
    username = "your_username" 
    password = "your_password"
    client.id = "your_oauth_app_client_id"
    client.secret = "your_oauth_app_client_secret"
}
```

## Run

To run the program you need to have installed on your computer the following:

- SBT - https://www.scala-sbt.org/
- Java in version 8 - https://java.com/en/download/
- Scala in version 12 - https://www.scala-lang.org/

if you have in your bash console order sbt you should run it in folder containing project using `sbt run`


