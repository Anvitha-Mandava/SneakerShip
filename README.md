# SneakerShip

SneakerShip is an Android app to mock a simple e-commerce application


**Screenshots**
![image](https://github.com/Anvitha-Mandava/SneakerShip/assets/139376656/9b8d7804-02bf-43d0-9576-dead443e223f)

![image](https://github.com/Anvitha-Mandava/SneakerShip/assets/139376656/5b18a01b-8fdc-4262-aa4e-b5c4d4d484e2)

![image](https://github.com/Anvitha-Mandava/SneakerShip/assets/139376656/1c54f9c9-9921-4de5-ba30-610d70e43dbf)

![image](https://github.com/Anvitha-Mandava/SneakerShip/assets/139376656/ed9bfb37-c2be-4e2d-8e0e-755ff69303d0)


**Features**

**Grid View: **Display a grid view of the top 100 sneakers, with a column size of 2. Each item includes the image, price, and name of the sneaker.
**Sort** based on the prices.
**Search**: Implement a search feature to find specific sneakers based on their names/brand 
**Sneaker Details**: Create a sneaker details page that shows the title, name, image, brand, year of release, and price of the selected sneaker.
**Add to Cart**: "Add to Cart" button on the sneaker details page allows users to add sneakers to the shopping cart.
**Checkout**: A checkout page to display the list of sneakers added to the cart, along with their images and prices, the total price of all the shoes at the end of the page.
**Cart Management**: To remove items from the cart individually to modify their selections.

**Development Decisions and Assumptions**

**MVVM Architecture: **The app follows the Model-View-ViewModel (MVVM) architecture pattern, separating the business logic (ViewModel) from the UI (View).
**Retrofit**: The Retrofit library is used for making HTTP requests to the backend API and fetching the sneaker data.
**RecyclerView**: The grid view is implemented using a RecyclerView with a GridLayoutManager to efficiently display and handle the top 100 sneakers.
**Image Loading:** Images of the sneakers are loaded using a library like  Picasso for efficient and optimized image loading and caching.
**ROOM Database:** A local SQLite database to store the user's shopping cart data.
**Persistence:** The user's shopping cart is persisted across app sessions, ensuring that items remain in the cart even if the user closes and reopens the app.

**Pagination** can be implemented using Paging 3 to handle large datasets, which will simplify the process of fetching and displaying data in chunks.

**Permissions**
Internet

**Installation**
cd ~/ProjectLocation/SneakerShip

git clone git@github.com:Anvitha-Mandava/SneakerShip.git

Build and Run your project
