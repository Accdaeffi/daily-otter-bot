package ru.itmo.iad.photorecognize.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.dao.ChatDao;
import ru.itmo.iad.photorecognize.domain.dto.OtterImageDto;
import ru.itmo.iad.photorecognize.telegram.Bot;
import ru.itmo.iad.photorecognize.telegram.response.PhotoResponse;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledService {

    private final ChatService chatService;

    private final OtterSendingHistoryService otterSendingHistoryService;

    private final OtterImageService otterImageService;

    private final Bot bot;

    public void sendOtterToAll() {
        List<ChatDao> chatDaoList = chatService.getChats();
        for (ChatDao chat : chatDaoList) {
            Optional<ObjectId> todaySent = otterSendingHistoryService.getSentToChatToday(chat.get_id());
            if (todaySent.isEmpty()) {
                try {
                    OtterImageDto imageDto = otterImageService.getRandomImage();
                    PhotoResponse photoResponse = new PhotoResponse(imageDto.getData(), imageDto.getFileName(), null, null);
                    photoResponse.send(bot, chat.getChatId());
                    otterSendingHistoryService.recordNewSending(chat.get_id(), imageDto.get_id());
                } catch (Exception ex) {
                    log.error("exception during daily otter sending!", ex);
                }
            }
        }
    }

}
