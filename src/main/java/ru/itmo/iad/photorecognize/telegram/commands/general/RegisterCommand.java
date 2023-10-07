package ru.itmo.iad.photorecognize.telegram.commands.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.service.ChatService;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

@Service
@Scope(scopeName = "prototype")
public class RegisterCommand extends AbsCommand {

    private final Long chatId;

    @Autowired
    private ChatService chatService;

    public RegisterCommand(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public Response<?> execute() {
        chatService.registerNewChat(chatId);
        return new StringResponse("Теперь я буду посылать вам выдр!");
    }
}
