----------------- Serj Tankian  ------------------------------------
INSERT INTO public.artista (art_nome, art_origem) VALUES ('Serj Tankian', 'Armênia');

INSERT INTO public.album (alb_nome) VALUES
('Harakiri'),('Black Blooms'),('The Rough Dog');

INSERT INTO public.artista_album (art_id, alb_id)
SELECT a.art_id, b.alb_id FROM public.artista a, public.album b
WHERE a.art_nome = 'Serj Tankian' AND b.alb_nome IN ('Harakiri','Black Blooms','The Rough Dog');

----------------- Mike Shinoda  ------------------------------------

INSERT INTO public.artista (art_nome, art_origem) VALUES ('Mike Shinoda', 'Estados Unidos');

INSERT INTO public.album (alb_nome) VALUES
('The Rising Tied'),
('Post Traumatic'),
('Post Traumatic EP'),
('Where''d You Go');



INSERT INTO public.artista_album (art_id, alb_id)
SELECT a.art_id, b.alb_id FROM public.artista a, public.album b
WHERE a.art_nome = 'Mike Shinoda' AND b.alb_nome IN ('The Rising Tied','Post Traumatic','Post Traumatic EP','Where''d You Go');


----------------- Michel Teló  ------------------------------------

INSERT INTO public.artista (art_nome, art_origem) VALUES ('Michel Teló', 'Brasil');

INSERT INTO public.album (alb_nome) VALUES
('Bem Sertanejo'),
('Bem Sertanejo - O Show (Ao Vivo)'),
('Bem Sertanejo - (1ª Temporada) - EP');

INSERT INTO public.artista_album (art_id, alb_id)
SELECT a.art_id, b.alb_id FROM public.artista a, public.album b
WHERE a.art_nome = 'Michel Teló' AND b.alb_nome IN ('Bem Sertanejo','Bem Sertanejo - O Show (Ao Vivo)','Bem Sertanejo - (1ª Temporada) - EP');

-----------------  Guns N'' Roses  ------------------------------------

INSERT INTO public.artista (art_nome, art_origem) VALUES ('Guns N'' Roses', 'Estados Unidos');

INSERT INTO public.album (alb_nome) VALUES
('Use Your Illusion I'),
('Use Your Illusion II'),
('Greatest Hits');


INSERT INTO public.artista_album (art_id, alb_id)
SELECT a.art_id, b.alb_id FROM public.artista a, public.album b
WHERE a.art_nome = 'Guns N'' Roses' AND b.alb_nome IN ('Use Your Illusion I','Use Your Illusion II','Greatest Hits');


-----------------  Rita Lee  ------------------------------------

INSERT INTO public.artista (art_nome, art_origem) VALUES ('Rita Lee', 'Brasil');

INSERT INTO public.album (alb_nome,alb_ano) VALUES
('Flerte Fatal','1987'),
('Aqui, Ali, em Qualquer Lugar','2000/2001'),
('Multishow ao Vivo: Rita Lee','22 de Maio de 2009');

INSERT INTO public.artista_album (art_id, alb_id)
SELECT a.art_id, b.alb_id FROM public.artista a, public.album b
WHERE a.art_nome = 'Rita Lee' AND b.alb_nome IN ('Flerte Fatal','Aqui, Ali, em Qualquer Lugar','Multishow ao Vivo: Rita Lee');

-----------------  Amy Winehouse  ------------------------------------


INSERT INTO public.artista (art_nome, art_origem) VALUES ('Amy Winehouse', 'Inglaterra');

INSERT INTO public.album (alb_nome,alb_ano) VALUES
('Frank',''),
('Back to Black','27 de outubro de 2006');

INSERT INTO public.artista_album (art_id, alb_id)
SELECT a.art_id, b.alb_id FROM public.artista a, public.album b
WHERE a.art_nome = 'Rita Lee' AND b.alb_nome IN ('Frank','Back to Black');
