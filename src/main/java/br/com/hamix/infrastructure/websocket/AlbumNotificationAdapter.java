package br.com.hamix.infrastructure.websocket;

import br.com.hamix.domain.gateway.AlbumNotificationGateway;
import br.com.hamix.domain.model.Album;
import br.com.hamix.infrastructure.websocket.dto.AlbumCreatedMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlbumNotificationAdapter implements AlbumNotificationGateway {
    private static final String DESTINATION = "/topic/albuns";

    private final SimpMessagingTemplate messagingTemplate;

    public AlbumNotificationAdapter(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void notifyAlbumCreated(Album album) {
        AlbumCreatedMessage message = AlbumCreatedMessage.builder()
                .id(album.getId())
                .nome(album.getNome())
                .ano(album.getAno())
                .build();
        messagingTemplate.convertAndSend(DESTINATION, message);
    }
}
