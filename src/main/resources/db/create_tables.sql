CREATE TABLE IF NOT EXISTS departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    patient_count INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS patients (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    department_id BIGINT NOT NULL REFERENCES departments(id)
);
