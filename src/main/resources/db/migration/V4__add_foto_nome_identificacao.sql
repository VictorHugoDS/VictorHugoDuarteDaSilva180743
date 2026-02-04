ALTER TABLE public.foto
ADD COLUMN IF NOT EXISTS nome_identificacao VARCHAR(255);

UPDATE public.foto
SET nome_identificacao = nome
WHERE nome_identificacao IS NULL;

ALTER TABLE public.foto
ALTER COLUMN nome_identificacao SET NOT NULL;
