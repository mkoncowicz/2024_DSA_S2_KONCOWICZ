CREATE TABLE "users" (
                        "id" SERIAL PRIMARY KEY,
                        "username" VARCHAR(255) NOT NULL,
                        "first_name" VARCHAR(255) NOT NULL,
                        "last_name" VARCHAR(255) NOT NULL,
                        "email" VARCHAR(255) NOT NULL UNIQUE,
                        "password" VARCHAR(255) NOT NULL,
                        "gender" VARCHAR(255) NOT NULL,
                        "day_of_birth" DATE NOT NULL
);