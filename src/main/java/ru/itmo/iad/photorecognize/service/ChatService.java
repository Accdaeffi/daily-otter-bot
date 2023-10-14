package ru.itmo.iad.photorecognize.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.dao.ChatDao;
import ru.itmo.iad.photorecognize.domain.repository.ChatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatDao getChatById(Long chatId) {
        return chatRepository.findFirstByChatId(chatId).orElse(null);
    }

    public List<ChatDao> getChats() {
        return chatRepository.findAll();
    }

    public void registerNewChat(Long chatId) {
        ChatDao chatDao = new ChatDao(ObjectId.get(), chatId);
        chatRepository.save(chatDao);
    }

}
