package ru.itmo.iad.photorecognize.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.dao.OtterSendingDao;
import ru.itmo.iad.photorecognize.domain.repository.OtterSendingRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OtterSendingHistoryService {

    private final OtterSendingRepository otterSendingRepository;

    public Optional<ObjectId> getSentToChatToday(ObjectId chatId) {
        LocalDate localDate = LocalDate.now();
        List<OtterSendingDao> sendings = otterSendingRepository.findByChatIdAndSendingDate(chatId, localDate);
        if (sendings.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(sendings.get(0).get_id());
        }
    }

    public void recordNewSending(ObjectId chatId, ObjectId imageId) {
        OtterSendingDao otterSendingDao = new OtterSendingDao(ObjectId.get(), imageId, chatId, LocalDate.now());
        otterSendingRepository.save(otterSendingDao);
    }
}
