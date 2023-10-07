package ru.itmo.iad.photorecognize.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import ru.itmo.iad.photorecognize.domain.dao.OtterImageDao;
import ru.itmo.iad.photorecognize.domain.dto.OtterImageDto;
import ru.itmo.iad.photorecognize.domain.repository.OtterImageRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtterImageService {

    private final GridFsTemplate gridFsTemplate;

    private final GridFsOperations operations;

    private final OtterImageRepository otterImageRepository;

    public ObjectId saveImage(String senderId, String senderUsername, File image) throws IOException {
        ObjectId fileId = gridFsTemplate.store(new FileInputStream(image), image.getName());

        var otterImage = OtterImageDao.builder()
                ._id(ObjectId.get())
                .fileId(fileId)
                .fileName(image.getName())
                .senderId(senderId)
                .senderUsername(senderUsername)
                .dtCreated(new Date())
                .checked(true)
                .build();

        otterImageRepository.save(otterImage);

        return otterImage.get_id();
    }

    public OtterImageDto getRandomImage() throws IllegalStateException, IOException {
        List<OtterImageDao> images = otterImageRepository.findByChecked(Boolean.TRUE);

        return convertDaoToDto(images.get(new Random().nextInt(images.size())));
    }

    private OtterImageDto convertDaoToDto(OtterImageDao dao) throws IllegalStateException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(dao.getFileId())));

        return new OtterImageDto(dao.get_id(),
                dao.getFileId().toHexString(),
                operations.getResource(file).getInputStream(),
                dao.getFileName());
    }
}
