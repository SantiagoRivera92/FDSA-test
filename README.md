# FDSA Destinations Test

This is a code test

The app is divided in three modules: **app**, **domain** and **data**


## Data module

The Data module is in charge of querying the API. It just uses Retrofit to query the API, but it uses an Interceptor to mock the API calls from assets.

Data is backed up locally using Room.

The Data module has no access to the domain or app modules.

## Domain module

The Domain module is in charge of transforming the raw data from the Data module into something the App module can use. It defines both the use cases (in this case there's just one, but additional use cases would go here) and a mapper that turns the raw response into our model.

The UseCases use Kotlin Corroutines to handle asynchronicity. They're defined as simple operations with an input and an output. The App module simply injects these use cases uses them for the output.

The Domain module has access to the Data module, but not the App module

## App module

The App module is where all the Android components go. The UI, Application, Activity and Fragment goes here.

Dependency injection is handled using Hilt.

The app has three main fragments:

### FdsaDestinationListFragment

This fragment loads the list of destinations. You get to filter all the results from the search action in the Action Bar, and navigate to the other two fragments (FdsaDestinationCreateFragment to create a new one, FdsaDestinationDetailFragment to review, edit and delete Destinations)

### FdsaDestinationDetailFragment

This fragment loads a clicked Destination through navArgs, then allows you to review it, delete it or edit it. When you try to edit it, the Fragment enters Edit Mode, which changes the entire layout through a visibility change and allows you to make changes to the Destination. If you do edit the Destination, the lastModify argument gets changed. When trying to modify our Room database, for an item with the same id, we take the one with the latest lastModify.

Deleting a Destination that is present in the mocked source will not work properly because as soon as we load said source again, the deleted Destination will be present again. Deleting Destinations you created yourself will work perfectly fine. 

### FdsaDestinationCreateFragment

This fragment allows you to create a new Destination. The part that handles creation was made with mocks in mind: Usually you'd create a new Destination, send it to the API (which would decide the id for the new destination since it's the source of truth), then query for the list of Destinations and update it once it's downloaded. Since our API is just a mock, we calculate a valid id for it and then save it in our Room database.

## Easter egg

Since working with country codes (especially when requiring user input for creation/edition of Destinations) is a bit cumbersome, I worked on a way to turn them into flag emojis. The entire emoji functionality is in com.santirivera.fdsa.utils.Emoji.kt

It works by turning those country codes into Regional Indicator emoji. For example, the emoji for the Spanish flag is made by combining the two Regional Indicator emoji for E and S (🇪 and 🇸), when put together you get 🇪🇸.

The Unicode value for 🇦 is 0x1F1E6, and the Unicode value for A is 0x41. By adding 0x1F1E6 and subtracting 0x41 (Or just adding 0x1F1E6-0x41 = 0x1F1A5) from each (uppercase) character in the country code, you get the equivalent Regional Indicator emoji. Then you just convert them to Characters, and append them together on a String and you get your flag emoji.

I spent more time than I should figuring this out, but I do like the result.
