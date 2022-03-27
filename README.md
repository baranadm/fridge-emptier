# fridge-emptier

Opinie, przemyslenia:
- moze lepiej nie oprozniacz-lodowki, tylko na-co-mam-ochote? w zalozeniu lodowka ogranicza skladniki.  
heroku: https://git.heroku.com/fridge-emptier.git

Service lets user write down ingredientDTOS from it's fridge, and ingredientDTOS that user does not like. The service then searches for recipies that contains every ingredientDTO from user's fridge, and does not contain unliked ingredientDTOS. User can view ingredientDTOS, summary and instructions of recipe. Recipe has origin URL, image URL, summary, ingredientDTOS, instructions. User can save recipies to favourites, delete and list-view them.

When user adds a recipe to favourites, it then is saved to database as an entity.

----
# nouns:
User
Ingredient -
Recipe -
Summary -
Origin URL
Image URL
Instruction -
Favourites
Database
List view
Details view

# verbs:
search
view list
view detail
save
delete
add