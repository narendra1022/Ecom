eCommerce Application :

This is a App-based eCommerce application built using Kotlin. The application allows customers to browse products, add items to their cart, and complete the checkout process. It also provides an admin dashboard for managing products, orders, and customers.

Features:

Browse products by category
Add items to the cart 
Create an account or checkout as a guest
View order history and order details
Admin dashboard for managing products, orders, and customers

To set up the application locally, follow these steps :

Clone the repository to your local machine.
Install the required dependencies using bundle install.
Set up the database 
Contributing

If you would like to contribute to the project, please follow these steps:

Fork the repository and create a new branch for your feature or bug fix.
Make your changes and ensure that all tests pass.
Submit a pull request with a clear description of your changes.

Libraries and technologies used :

Navigation component : one activity contains multiple fragments instead of creating multiple activites.
Firebase Auth : Manging Accounts/ Loging in and Registrations.
Firebase Firestore : Database for the system.
Firebase Storage : To store proucts images and user profile pictures.
MVVM & LiveData : Saperate logic code from views and save the state in case the screen configuration changes.
Coroutines : do some code in the background.
view binding : instead of inflating views manually view binding will take care of that.
Glide : Catch images and load them in imageView.
