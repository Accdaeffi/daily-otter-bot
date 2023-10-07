package ru.itmo.iad.photorecognize.telegram.commands.photos;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.itmo.iad.photorecognize.service.OtterImageService;
import ru.itmo.iad.photorecognize.service.PhotoGetter;
import ru.itmo.iad.photorecognize.telegram.commands.AbsCommand;
import ru.itmo.iad.photorecognize.telegram.response.Response;
import ru.itmo.iad.photorecognize.telegram.response.StringResponse;

import java.io.File;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@Scope(scopeName = "prototype")
public class SaveNewOtterPhotoCommand extends AbsCommand {

    @Autowired
    OtterImageService imageService;

    @Autowired
    PhotoGetter photoGetter;

    private final List<PhotoSize> photoSizes;

    private final User sender;

    public SaveNewOtterPhotoCommand(User user, List<PhotoSize> photoSizes) {
        this.sender = user;
        this.photoSizes = photoSizes;
    }

    @Override
    public Response<?> execute() {
        log.info("Received otter photo to save!");

        PhotoSize photo = photoSizes.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);
        File image = photoGetter.getUserImage(photo);
        try {
            imageService.saveImage(sender.getId().toString(), sender.getUserName(), image);
            return new StringResponse("Отправлено на проверку выдрянности!");
        } catch (Exception ex) {
            log.error("Ошибка при сохранение выдры!", ex);
            return new StringResponse("Ошибка!");
        }
    }
}
