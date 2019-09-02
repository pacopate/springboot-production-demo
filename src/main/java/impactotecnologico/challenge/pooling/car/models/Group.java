package impactotecnologico.challenge.pooling.car.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Group {

	@Id
	@JsonIgnore
	private ObjectId _id;

	@Getter
	@Setter
	@JsonProperty(value = "id")
	private int externalId;

	@Getter
	@Setter
	private int people;

}
