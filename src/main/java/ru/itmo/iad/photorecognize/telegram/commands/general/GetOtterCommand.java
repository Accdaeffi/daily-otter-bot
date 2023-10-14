package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.dao.ChatDao;
import ru.itmo.iad.photorecognize.domain.dto.OtterImageDto;
import ru.itmo.iad.photorecognize.service.ChatService;
import ru.itmo.iad.photorecognize.service.OtterImageService;
import ru.itmo.iad.photorecognize.service.OtterSendingHistoryService;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.PhotoResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

import java.io.IOException;
import java.util.Optional;

@Service
@Scope("prototype")
public class GetOtterCommand extends AbsCommand {

    @Autowired
    OtterSendingHistoryService otterSendingHistoryService;

    @Autowired
    OtterImageService otterImageService;

    @Autowired
    ChatService chatService;

    private final Long chatId;

    public GetOtterCommand(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public Response<?> execute() {
        ChatDao chat = chatService.getChatById(chatId);
        if (chat != null) {
            Optional<ObjectId> todaySent = otterSendingHistoryService.getSentToChatToday(chat.get_id());
            try {
                //if (todaySent.isEmpty()) {
                    OtterImageDto imageDto = otterImageService.getRandomImage();
                    PhotoResponse photoResponse = new PhotoResponse(imageDto.getData(), imageDto.getFileName(), null, null);
                    otterSendingHistoryService.recordNewSending(chat.get_id(), imageDto.get_id());
                    return photoResponse;
//                } else {
//                    return new StringResponse("Уже получали порцию милоты!");
//                }
            } catch (IOException e) {
                return new StringResponse("Ошибка при получении выдры :(");
            }
        } else {
            return new StringResponse("Ваш чат не добавлен как выдрочат, напишите /register!");
        }
    }
}
