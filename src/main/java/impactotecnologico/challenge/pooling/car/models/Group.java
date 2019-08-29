package impactotecnologico.challenge.pooling.car.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	private int id;

	@Getter
	@Setter
	private int people;

	@Getter
	@Setter
	@JsonIgnore
	private boolean traveling;
}
