package br.com.hamix.usecase.album.savefoto;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class SaveFotosService implements SaveFotosUseCase{
    @Override
    public void salvarFotosAlbum(Integer idAlbum, List<MultipartFile> multipartFileList) {
    }
}
