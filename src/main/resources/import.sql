INSERT INTO role(role_name) VALUES(0);
INSERT INTO role(role_name) VALUES(1);
INSERT INTO role(role_name) VALUES(2);

INSERT INTO categories(category) VALUES('ACTION')
INSERT INTO categories(category) VALUES('DRAMA')
INSERT INTO categories(category) VALUES('COMEDY')
INSERT INTO categories(category) VALUES('FANTASY')
INSERT INTO categories(category) VALUES('ROMANCE')

INSERT INTO anime(title, episodes, status, author_name, description, img_url) VALUES('Attack on Titan', 250, 'COMPLETE', 'Kukuku', 'Gigante', 'https://icon-aot.com.br/');
INSERT INTO anime(title, episodes, status, author_name, description, img_url) VALUES('100 Things to Do Before You Become a Zombie', 114, 'AIRING', 'Haro Aso e Kotaro Takata', 'Akira via sua vida sendo completamente sugada por uma empresa tóxica. No enquanto, quando de repente sua cidade é assolada por um apocalipse zumbi, ele acha um novo sentido na vida.', 'https://icon-100zombies.com.br/');

INSERT INTO category_anime(anime_id, categories_id) VALUES(1, 1)