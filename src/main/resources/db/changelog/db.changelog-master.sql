--liquibase formatted sql

--changeset convocatoria:1-create-franchise
-- Premise: Creating the main franchise table containing core brand details.
CREATE TABLE franchise_entity
(
    id          UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    CONSTRAINT pk_franchise_entity PRIMARY KEY (id)
);

--changeset convocatoria:2-create-branch
-- Premise: Creating the branch table with a foreign key referencing its managing franchise.
CREATE TABLE branch_entity
(
    id           UUID         NOT NULL,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(500),
    address      VARCHAR(255),
    franchise_id UUID         NOT NULL,
    CONSTRAINT pk_branch_entity PRIMARY KEY (id),
    CONSTRAINT fk_branch_franchise FOREIGN KEY (franchise_id) REFERENCES franchise_entity (id)
);

--changeset convocatoria:3-create-product
-- Premise: Creating the core product catalog table.
CREATE TABLE product_entity
(
    id          UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price       DOUBLE PRECISION,
    CONSTRAINT pk_product_entity PRIMARY KEY (id)
);

--changeset convocatoria:4-create-product-branch
-- Premise: Creating the bridge table managing inventory per product per branch.
CREATE TABLE product_branch_entity
(
    id         UUID NOT NULL,
    branch_id  UUID NOT NULL,
    product_id UUID NOT NULL,
    stock      INTEGER,
    CONSTRAINT pk_product_branch_entity PRIMARY KEY (id),
    CONSTRAINT fk_prod_branch_branch FOREIGN KEY (branch_id) REFERENCES branch_entity (id),
    CONSTRAINT fk_prod_branch_product FOREIGN KEY (product_id) REFERENCES product_entity (id),
    CONSTRAINT uk_branch_product UNIQUE (branch_id, product_id)
);

--changeset convocatoria:5-seed-mock-data context:dev,test
INSERT INTO franchise_entity (id, name, description)
VALUES ('4f9b3b1e-128d-4e92-913a-a55e1dbd9201', 'Franchise test 1', 'Test franchise.'),
       ('c8936ef0-be3c-4137-97d3-df62eb04de62', 'Good Ol Pancakes', 'Premium grade pancakes.');

INSERT INTO branch_entity (id, name, description, address, franchise_id)
VALUES ('84ba7133-cb20-410a-b673-90be5c9db9d1', 'Downtown Franchise', 'Downtown location.',
        '123 Main Street, Metropolis', '4f9b3b1e-128d-4e92-913a-a55e1dbd9201'),
       ('fa7a26f8-4b72-4e67-9207-6b45b23d9a10', 'Suburban Franchise', 'Drive-thru focused location.',
        '6767 Heaven St. Avenue, Suburbia', '4f9b3b1e-128d-4e92-913a-a55e1dbd9201'),
       ('09be696c-13da-47e0-9bc8-d3c5ee9be80c', 'Good Ol Pancakes Downtown', 'Downtown location.',
        '45 Wall Street, Russia', 'c8936ef0-be3c-4137-97d3-df62eb04de62');

INSERT INTO product_entity (id, name, description, price)
VALUES ('e68407fc-8f78-4bfb-b6d8-1fc45bfae101', 'Classic Cheeseburger', 'Beef patty with cheese, pickles, and sauce.',
        5.99),
       ('d2b0e9db-6fe2-4b2a-bf35-a7459638dc52', 'Large French Fries', 'Potato fries.', 2.49),
       ('a58cd01b-bf2e-4b7f-ad8b-96791b7d5267', 'Super Pancake', 'Premium grade pancake, made with love.', 4.75);

INSERT INTO product_branch_entity (id, stock, branch_id, product_id)
VALUES ('1a2b3c4d-5e6f-7a8b-9c0d-1e2f3a4b5c6d', 150, '84ba7133-cb20-410a-b673-90be5c9db9d1',
        'e68407fc-8f78-4bfb-b6d8-1fc45bfae101'),
       ('2b3c4d5e-6f7a-8b9c-0d1e-2f3a4b5c6d7e', 300, '84ba7133-cb20-410a-b673-90be5c9db9d1',
        'd2b0e9db-6fe2-4b2a-bf35-a7459638dc52'),
       ('3c4d5e6f-7a8b-9c0d-1e2f-3a4b5c6d7e8f', 85, 'fa7a26f8-4b72-4e67-9207-6b45b23d9a10',
        'e68407fc-8f78-4bfb-b6d8-1fc45bfae101'),
       ('4d5e6f7a-8b9c-0d1e-2f3a-4b5c6d7e8f9a', 500, '09be696c-13da-47e0-9bc8-d3c5ee9be80c',
        'a58cd01b-bf2e-4b7f-ad8b-96791b7d5267');

--changeset convocatoria:6-create-role
-- Premise: Creating the authorization roles lookup table.
CREATE TABLE role_entity
(
    id   UUID        NOT NULL,
    role VARCHAR(15) NOT NULL,
    CONSTRAINT pk_role_entity PRIMARY KEY (id)
);

--changeset convocatoria:7-create-user
-- Premise: Creating the application user accounts table.
CREATE TABLE user_entity
(
    id       UUID         NOT NULL,
    username VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    active   BOOLEAN      NOT NULL,
    CONSTRAINT pk_user_entity PRIMARY KEY (id),
    CONSTRAINT uk_user_username UNIQUE (username),
    CONSTRAINT uk_user_email UNIQUE (email)
);

--changeset convocatoria:8-create-user-roles
-- Premise: Creating the join table mapping users to their respective multiple roles.
CREATE TABLE user_roles
(
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES user_entity (id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES role_entity (id) ON DELETE CASCADE
);


--changeset convocatoria:9-add-sku-to-product
-- Premise: Adding SKU column to product_entity.

ALTER TABLE product_entity ADD COLUMN sku VARCHAR(50);

UPDATE product_entity SET sku = 'SKU-A123-BURGER' WHERE id = 'e68407fc-8f78-4bfb-b6d8-1fc45bfae101';
UPDATE product_entity SET sku = 'SKU-B123-FRIES' WHERE id = 'd2b0e9db-6fe2-4b2a-bf35-a7459638dc52';
UPDATE product_entity SET sku = 'SKU-C123-PANCAKE' WHERE id = 'a58cd01b-bf2e-4b7f-ad8b-96791b7d5267';

UPDATE product_entity SET sku = CONCAT('SKU-GEN-', SUBSTR(CAST(id AS VARCHAR), 1, 8)) WHERE sku IS NULL;

ALTER TABLE product_entity ALTER COLUMN sku SET NOT NULL;

ALTER TABLE product_entity ADD CONSTRAINT uk_product_sku UNIQUE (sku);