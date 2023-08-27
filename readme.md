# DsCatalog API

[![Docker Hub Repo](https://img.shields.io/docker/pulls/reinan07/dscatalog:lastet.svg)](https://hub.docker.com/repository/docker/reinan07/dscatalog)

A simple API named DsCatalog, which stands for "Digital Store Catalog," provides basic CRUD (Create, Read, Update, Delete) functionality for managing products, categories, and users. The API includes various endpoints to perform operations on these entities.

## Endpoints

### 1. Products

#### Get all products

- Endpoint: `/products`
- Method: GET
- Description: Retrieves a list of all products.
- Required Role: ROLE_ADMIN, ROLE_OPERATOR

#### Get a product by ID

- Endpoint: `/products/{id}`
- Method: GET
- Description: Retrieves a specific product by its ID.
- Required Role: ROLE_ADMIN, ROLE_OPERATOR

#### Create a new product

- Endpoint: `/products`
- Method: POST
- Description: Creates a new product.
- Required Role: ROLE_ADMIN

#### Update a product

- Endpoint: `/products/{id}`
- Method: PUT
- Description: Updates an existing product.
- Required Role: ROLE_ADMIN

#### Delete a product

- Endpoint: `/products/{id}`
- Method: DELETE
- Description: Deletes a product.
- Required Role: ROLE_ADMIN

### 2. Categories

#### Get all categories

- Endpoint: `/categories`
- Method: GET
- Description: Retrieves a list of all categories.
- Required Role: ROLE_ADMIN, ROLE_OPERATOR

#### Get a category by ID

- Endpoint: `/categories/{id}`
- Method: GET
- Description: Retrieves a specific category by its ID.
- Required Role: ROLE_ADMIN, ROLE_OPERATOR

#### Create a new category

- Endpoint: `/categories`
- Method: POST
- Description: Creates a new category.
- Required Role: ROLE_ADMIN

#### Update a category

- Endpoint: `/categories/{id}`
- Method: PUT
- Description: Updates an existing category.
- Required Role: ROLE_ADMIN

#### Delete a category

- Endpoint: `/categories/{id}`
- Method: DELETE
- Description: Deletes a category.
- Required Role: ROLE_ADMIN

### 3. Users

#### Get all users

- Endpoint: `/users`
- Method: GET
- Description: Retrieves a list of all users.
- Required Role: ROLE_ADMIN

#### Get a user by ID

- Endpoint: `/users/{id}`
- Method: GET
- Description: Retrieves a specific user by their ID.
- Required Role: ROLE_ADMIN

#### Create a new user

- Endpoint: `/users`
- Method: POST
- Description: Creates a new user.
- Required Role: ROLE_ADMIN

#### Update a user

- Endpoint: `/users/{id}`
- Method: PUT
- Description: Updates an existing user.
- Required Role: ROLE_ADMIN

#### Delete a user

- Endpoint: `/users/{id}`
- Method: DELETE
- Description: Deletes a user.
- Required Role: ROLE_ADMIN

## Roles

The API implements role-based access control (RBAC) using the following roles:

1. `ROLE_ADMIN`: Has full administrative privileges and can perform all operations.
2. `ROLE_OPERATOR`: Has limited privileges and can perform specific operations.

## Authentication and Authorization

To access the API endpoints, you need to authenticate and provide the appropriate authorization token in the request header. The API uses a token-based authentication mechanism such as JWT (JSON Web Token). You should include the token in the `Authorization` header with the value `Bearer {token}`.

Each endpoint specifies the required role(s) to access it. The API will validate the user's role before allowing or denying access.

## Error Handling

The API provides appropriate error responses in case of invalid requests, missing parameters, or insufficient privileges. The response will include an appropriate HTTP status code and a descriptive
