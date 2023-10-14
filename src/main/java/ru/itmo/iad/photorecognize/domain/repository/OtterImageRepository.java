package ru.itmo.iad.photorecognize.domain.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.itmo.iad.photorecognize.domain.dao.OtterImageDao;

import java.util.List;
import java.util.Optional;

public interface OtterImageRepository extends MongoRepository<OtterImageDao, ObjectId> {

    @Query(value = "{ checked : ?0}")
    List<OtterImageDao> findByChecked(Boolean checked);

    Optional<OtterImageDao> findFirstByFileId(ObjectId fileId);

}
