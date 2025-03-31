Para crear la base de datos, se utilizará PostgreSQL. En el PgAdmin se debe crear una nueva base de datos que almacenará la información de la aplicación. La base de datos original se ha llamado "wordshake".

Para crear las tablas de la base de datos y propiedades iniciales, utiliza el siguiente Script:
```roomsql
-- Tabla Jugador
CREATE TABLE Player (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    signup_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Tabla Partida
CREATE TABLE Game (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT NOT NULL,
    start_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    end_date DATETIME,
    total_score INT DEFAULT 0,
    duration INT NOT NULL DEAFULT 0, -- duración en segundos
    FOREIGN KEY (player_id) REFERENCES Player(id)
);

-- Tabla Board (Tablero de letras)
CREATE TABLE Board (
    id INT AUTO_INCREMENT PRIMARY KEY,
    game_id INT NOT NULL,
    letras VARCHAR(50) NOT NULL,
    FOREIGN KEY (game_id) REFERENCES Game(id)
);

-- Tabla Palabra
CREATE TABLE Word (
    id INT AUTO_INCREMENT PRIMARY KEY,
    word VARCHAR(50) NOT NULL UNIQUE,
    difficulty ENUM('easy', 'normal', 'hard') NOT NULL DEAFULT 'easy'
);

-- Tabla PalabraEncontrada
CREATE TABLE FoundWord (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_game INT NOT NULL,
    id_word INT NOT NULL,
    time_to_find INT NOT NULL DEAFULT 0, -- segundos transcurridos desde el inicio de la partida
    FOREIGN KEY (id_game) REFERENCES Game(id),
    FOREIGN KEY (id_word) REFERENCES Word(id)
);

```