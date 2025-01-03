
Shopster
==============

Overview
--------
Shopster is a Spring-based web application offering user authentication, product listing and selling, cart functionality, payment integration using Stripe, and a password reset feature. It is designed to provide a streamlined user experience for buying and selling products online.

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

4. **Password Reset**
   - Password reset via email with a time-bound reset token.

5. **Admin and Utility Functions**
   - Serve uploaded images dynamically.
   - Clear cart after successful payment.

Installation
------------

# Prerequisites
- Java Development Kit (JDK) 17+
- Apache Maven
- PostgreSQL (or another compatible database)
- A valid Stripe API key
- SMTP server details for email functionality

# Setup Instructions
1. **Clone the Repository**
   ```
   git clone https://github.com/username/lockedin-myapp.git
   cd lockedin-myapp
   ```

2. **Configure Environment Variables**
   Create an `application.properties` file under `src/main/resources` with the following content:
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/myapp
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password
   stripe.api.key=your_stripe_api_key
   spring.mail.host=smtp.your-email.com
   spring.mail.port=587
   spring.mail.username=your_email@example.com
   spring.mail.password=your_email_password
   ```

3. **Build and Run the Application**
   ```
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the Application**
   Open a browser and navigate to `http://localhost:8080`.

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
