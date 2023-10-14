package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.itmo.iad.photorecognize.service.OtterImageService;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.EditMessageCaptionResponse;
import ru.itmo.iad.photorecognize.telegram.response.Response;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("prototype")
public class RecordCheckingCommand extends AbsCommand {

    @Autowired
    OtterImageService otterImageService;

    @Autowired
    List<String> checkers;

    private final User user;
    private final ObjectId photoId;
    private final boolean result;
    private final int messageId;

    public RecordCheckingCommand(User user, String argument, int messageId) {
        this.user = user;
        String[] splittedArgument = argument.split(" ", 2);
        this.photoId = new ObjectId(splittedArgument[0]);
        this.result = Boolean.parseBoolean(splittedArgument[1]);

        this.messageId = messageId;
    }

    @Override
    public Response<?> execute() {
        if (checkers.contains(user.getId().toString())) {
            otterImageService.completeChecking(photoId, result);

            return new EditMessageCaptionResponse(
                    "Спасибо за оценку!",
                    messageId,
                    new InlineKeyboardMarkup(new ArrayList<>())
            );
        } else {
            return null;
        }
    }
}
