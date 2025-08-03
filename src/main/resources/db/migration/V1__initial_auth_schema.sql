-- V1__initial_auth_schema.sql
-- 1) Tabla users
CREATE TABLE
    users (
        id BIGSERIAL PRIMARY KEY,
        username VARCHAR(50) NOT NULL UNIQUE,
        password_hash VARCHAR(200) NOT NULL,
        email VARCHAR(150) NOT NULL UNIQUE,
        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW (),
        created_by BIGINT REFERENCES users (id),
        updated_at TIMESTAMPTZ,
        updated_by BIGINT REFERENCES users (id)
    );

CREATE INDEX idx_users_status ON users (status);

-- 2) Tabla employees
CREATE TABLE
    employees (
        id BIGSERIAL PRIMARY KEY,
        first_name VARCHAR(100) NOT NULL,
        last_name VARCHAR(100) NOT NULL,
        employee_number VARCHAR(50) NOT NULL UNIQUE,
        position VARCHAR(100) NOT NULL,
        user_id BIGINT NULL REFERENCES users (id),
        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW (),
        created_by BIGINT REFERENCES users (id),
        updated_at TIMESTAMPTZ,
        updated_by BIGINT REFERENCES users (id)
    );

CREATE INDEX idx_employees_status ON employees (status);

-- 3) Tabla roles
CREATE TABLE
    roles (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(50) NOT NULL UNIQUE,
        description VARCHAR(200) NULL,
        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW (),
        created_by BIGINT REFERENCES users (id),
        updated_at TIMESTAMPTZ,
        updated_by BIGINT REFERENCES users (id)
    );

CREATE INDEX idx_roles_status ON roles (status);

-- 4) Tabla permissions
CREATE TABLE
    permissions (
        id BIGSERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        code VARCHAR(100) NOT NULL UNIQUE,
        description TEXT,
        parent_permission_id BIGINT REFERENCES permissions (id),
        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW (),
        created_by BIGINT REFERENCES users (id),
        updated_at TIMESTAMPTZ,
        updated_by BIGINT REFERENCES users (id)
    );

CREATE INDEX idx_permissions_status ON permissions (status);

CREATE INDEX idx_permissions_parent ON permissions (parent_permission_id);

-- 5) Tabla user_roles
CREATE TABLE
    user_roles (
        user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
        role_id BIGINT NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW (),
        created_by BIGINT REFERENCES users (id),
        updated_at TIMESTAMPTZ,
        updated_by BIGINT REFERENCES users (id),
        PRIMARY KEY (user_id, role_id)
    );

CREATE INDEX idx_user_roles_status ON user_roles (status);

-- 6) Tabla role_permissions
CREATE TABLE
    role_permissions (
        role_id BIGINT NOT NULL REFERENCES roles (id) ON DELETE CASCADE,
        permission_id BIGINT NOT NULL REFERENCES permissions (id) ON DELETE CASCADE,
        status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW (),
        created_by BIGINT REFERENCES users (id),
        updated_at TIMESTAMPTZ,
        updated_by BIGINT REFERENCES users (id),
        PRIMARY KEY (role_id, permission_id)
    );

CREATE INDEX idx_role_permissions_status ON role_permissions (status);