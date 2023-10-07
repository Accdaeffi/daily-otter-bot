package ru.itmo.iad.photorecognize.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;

import java.io.InputStream;

@Data
@AllArgsConstructor
public class OtterImageDto {

    ObjectId _id;
    String photoId;
    InputStream data;
    String fileName;

}
