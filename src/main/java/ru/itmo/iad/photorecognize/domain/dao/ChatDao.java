package ru.itmo.iad.photorecognize.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "chat")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatDao {

    @Id
    @Field
    private ObjectId _id;

    @Field
    private Long chatId;
}
