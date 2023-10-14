package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.itmo.iad.photorecognize.domain.dto.OtterImageDto;
import ru.itmo.iad.photorecognize.service.OtterImageService;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.keyboard.CheckOtterKeyboard;
import ru.itmo.iad.photorecognize.telegram.response.PhotoResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

import java.util.List;

@Service
@Scope("prototype")
public class CheckOtterCommand extends AbsCommand {

    User messageAuthor;

    @Autowired
    List<String> checkers;

    @Autowired
    OtterImageService otterImageService;

    @Autowired
    CheckOtterKeyboard checkOtterKeyboard;

    public CheckOtterCommand(User messageAuthor) {
        this.messageAuthor = messageAuthor;
    }

    @Override
    public Response<?> execute() {
        if (checkers.contains(messageAuthor.getId().toString())) {
            try {
                OtterImageDto image = otterImageService.getUnckeckedImage();
                if (image != null) {
                    return new PhotoResponse(image.getData(), image.get_id().toString(), null,
                            checkOtterKeyboard.getKeyboard(image.getPhotoId()));
                } else {
                    return new StringResponse("Нет изображений для проверки!");
                }
            } catch (Exception ex) {
                return new StringResponse("Ошибка при получении изображения!");
            }
        } else {
            return new StringResponse("Недостаточно прав!");
        }
    }
}
