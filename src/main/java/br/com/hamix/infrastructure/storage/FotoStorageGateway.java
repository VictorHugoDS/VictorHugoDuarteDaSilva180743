package br.com.hamix.infrastructure.storage;

import br.com.hamix.domain.model.Foto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FotoStorageGateway {
    Foto uploadFoto(MultipartFile file);
    String recuperarLinksFotos(Foto foto);
}
