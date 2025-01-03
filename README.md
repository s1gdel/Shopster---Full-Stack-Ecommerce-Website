
Shopster
==============

Overview
--------
Shopster is a Spring-based web application emulating an e-commerce website offering user authentication, product listing and selling, cart functionality and payment integration using Stripe. It is designed to provide a streamlined user experience for buying and selling products online.

Features
--------
1. **User Authentication**
   - Custom login page for user authentication.
   - Signup functionality with validation to prevent duplicate emails.
   - Profile management with session-based authentication.

2. **Product Management**
   - Browse products with search and price filtering.
   - List new products for sale with image uploads.

3. **Cart Functionality**
   - Add items to a cart.
   - Checkout and payment processing using Stripe integration.
   - Success and cancellation pages for payment flows.
4. **Buying Products Using Stripe**
   - Securely purchase products using Stripe's test mode.
   - Automatically clear the cart upon successful purchase.
   - Notify users of transaction success or failure.


Installation
------------

# Prerequisites
- Java Development Kit (JDK) 17+
- Apache Maven
- PostgreSQL (or another compatible database)
- A valid Stripe API key
- SMTP server details for email functionality

Usage
-----

# Endpoints

- `/custom-login`: Login page for existing users.
- `/signup`: Signup page for new users.
- `/products`: View products with optional search and filters.
- `/sell`: Form to list a new product.
- `/cart`: View items in the cart.
- `/cart/buy`: Initiates the Stripe payment process.
- `/forgot-password`: Request password reset.
- `/reset-password`: Resets password using a token.

# Image Uploads
Images for products are stored in the `uploads` directory. Configure the path as needed in the `HelloController`.

# Payment Integration
Stripe is used for payment processing. Ensure you have a valid Stripe API key and configure it in the environment variables.

# Email Functionality
Emails for password reset are sent using the configured SMTP server. Update the `application.properties` file with valid credentials.

Technologies Used
-----------------
- **Spring Framework** (Boot, MVC, Data JPA, Mail)
- **Thymeleaf** for templates
- **Stripe** for payment processing
- **MySQL** for database
- **JavaMailSender** for email functionality





![Login](https://github.com/user-attachments/assets/2419f108-551f-4637-9873-21465907df88)
![Products](https://github.com/user-attachments/assets/711ded22-ec0a-4957-bade-32ef13bdf447)
![Sell](https://github.com/user-attachments/assets/69d42f59-456c-4bde-b388-406d55be93dc)
![Cart](https://github.com/user-attachments/assets/07c1a42b-c057-4db3-a783-d1b7e6db29c9)
![Profile](https://github.com/user-attachments/assets/e5f6ca4f-d802-497b-9ef9-ef9c8d2bfb5c)
![Stripe](https://github.com/user-attachments/assets/95bab96f-5930-4625-bdcd-76da8ff5985a)
