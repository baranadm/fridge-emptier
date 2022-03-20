# fridge-emptier

Service lets user write down ingredients from it's fridge, and ingredients that user does not like. The service then searches for recipies that contains every ingredient from user's fridge, and does not contain unliked ingredients. User can view ingredients, summary and instructions of recipe. Recipe has origin URL, image URL, summary, ingredients, instructions. User can save recipies to favourites, delete and list-view them.

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