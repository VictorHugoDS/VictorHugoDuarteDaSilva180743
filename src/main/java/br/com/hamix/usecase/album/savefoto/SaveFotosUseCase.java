package br.com.hamix.usecase.album.savefoto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SaveFotosUseCase {
    void salvarFotosAlbum (Integer idAlbum, List<MultipartFile> multipartFileList);
}
