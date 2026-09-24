# Demo Softpos Backend EVO

Web Application that simulates the SoftPos backend to obtain the RequestURI.

To complete the request, you can either fill in the form fields or set the default values ​​in the `Constant.java` class.

* DOMAIN: STAGING / PRODUCTION
* CERTIFICATE: X.509 certificate in PEM format containing Base64-encoded data uploaded in the portal.   
* PRIVATE_KEY: Unencrypted cryptographic private key containing Base64-encoded data.
* ID_CLIENT: Client ID identifier for the application registered on the Nexi developer portal.                              
* SECRET: Secret key identifying the application registered on the Nexi developer portal.                                   
* APP_REDIRECT_URI: Web address specified in the app registered on the Nexi developer portal and it must be correctly formatted (e.s. https://mywebsite/myredirectpage).
* ID_POINT_OF_SALE: Point of Sale ID assigned to your application in the portal.                        
* TERMINAL_ID: Terminal ID associated with the Point of Sale.
* DEVICE_ID: Alphanumeric ID identifying the device making the request. During the testing phase, it can also be a randomly generated value.
* USERNAME_MERCHANT: The Username Merchant is the email address associated with the user.

In the `application.properties` file, you can configure the port and the application name. By default, it is set to `http://localhost:8081/softpos-backend-evolution/`.
  
## Reference

This project template uses:

* Java 21
* [Spring Boot](https://spring.io/projects/spring-boot) Create stand-alone Spring applications
* [Spring Web](https://spring.io/guides/gs/serving-web-content/) to serve HTTP requests
* [Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html) for HTML-templating
* [Bootstrap](https://getbootstrap.com/docs/4.0/getting-started/introduction/) framework for building responsive template starter page.


