package ru.itmo.iad.photorecognize.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "otter_sending")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtterSendingDao {

    @Id
    @Field
    private ObjectId _id;

    @Field
    private ObjectId imageId;

    @Field
    private ObjectId chatId;

    @Field
    private LocalDate sendingDate;
}
