# fridge-emptier

## Site URL
Heroku: https://fridge-emptier.herokuapp.com/

## Description
Service lets user write down ingredients available in his kitchen. User also is allowed to provide ingredients, that he does not like. The service then searches for recipes containing ingredients provided by user, and not containing disliked ingredients. User can view ingredients, summary and instructions of recipe. Recipe has origin URL, image URL, summary, ingredients, instructions.

Service is retrieving data from external API: https://spoonacular.com/food-api  
Requests are processed through Rapid API: https://rapidapi.com/

## Technologies/frameworks used in project:
- HTML (Bootstrap) + CSS + Thymeleaf
- SpringBoot
- JUnit Jupiter
- OkHttp (+ MockWebServer for tests purposes)
- ModelMapper (with Property Mappings and Converters)
- Jackson (ObjectMapper)
- Lombok