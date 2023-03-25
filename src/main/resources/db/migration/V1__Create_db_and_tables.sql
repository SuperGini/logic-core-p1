CREATE TABLE IF NOT EXISTS users
(
    id              BIGINT       NOT NULL UNIQUE,
    email           VARCHAR(60)  NOT NULL DEFAULT 'N/A',
    password        VARCHAR(255) NOT NULL,
    username        VARCHAR(60)  NOT NULL,
    profile_picture VARBINARY(255),
    version         SMALLINT,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS folders
(
    id                      BIGINT NOT NULL UNIQUE,
    create_date              DATETIME,
    current_folder_capacity DECIMAL(6, 2),
    folder_capacity         DECIMAL(6, 2),
    last_update_user        VARCHAR(60),
    documents_number        INT,
    images_number           INT,
    videos_number           INT,
    other_files_number      INT,
    project_name            VARCHAR(100),
    update_date             DATETIME,
    version                 SMALLINT,
    user_id                 BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS files
(
    id          BIGINT NOT NULL UNIQUE,
    file        TINYBLOB,
    file_format VARCHAR(10),
    file_type   VARCHAR(100),
    folder_id   BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (folder_id) REFERENCES folders (id)
);
