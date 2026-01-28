package br.com.hamix.infrastructure.storage.minio;

import br.com.hamix.config.exception.custom.StorageException;
import br.com.hamix.domain.model.Foto;
import br.com.hamix.infrastructure.storage.FotoStorageGateway;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class FotoStorageAdapter implements FotoStorageGateway {

    private final MinioClient client;
    private final String bucket;
    private final String expirationTime;

    public FotoStorageAdapter(MinioClient client,
                              @Value("${minio.bucket}") String bucket,
                              @Value("${minio.expiration-time}") String expirationTime) {
        this.client = client;
        this.bucket = bucket;
        this.expirationTime = expirationTime;
    }


    @Override
    public Foto uploadFoto(MultipartFile file) {
        try {
            String nomeArquivo  = file.getOriginalFilename();
            String nomeIdentificacaoFoto = UUID.randomUUID() + "-" + nomeArquivo;
            client.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(nomeIdentificacaoFoto)
                            .stream(file.getInputStream(),
                                    file.getSize(),
                                    -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return Foto.builder().nome(nomeArquivo).nomeIdentificacao(nomeIdentificacaoFoto).build();
        } catch (Exception e){
            throw new StorageException("Erro ao realizar upload da foto",e);
        }

    }

    @Override
    public Foto recuperarLinkFoto(Foto foto) {
        try {
            String link =  client.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(foto.getNomeIdentificacao())
                            .expiry(Integer.parseInt(expirationTime))
                            .build()
            );
            foto.setUrl(link);
            return foto;
        } catch (Exception e){
            throw new StorageException("Erro ao recuperar url da foto",e);
        }
    }
}
