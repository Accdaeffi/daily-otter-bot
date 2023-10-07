package ru.itmo.iad.photorecognize.domain.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.itmo.iad.photorecognize.domain.dao.OtterSendingDao;

import java.time.LocalDate;
import java.util.List;

public interface OtterSendingRepository extends MongoRepository<OtterSendingDao, ObjectId> {

    @Query(value = "{ chatId : ?0, sendingDate : ?1}")
    List<OtterSendingDao> findByChatIdAndSendingDate(ObjectId chatId, LocalDate sendingDate);

}
