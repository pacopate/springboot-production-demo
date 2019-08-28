package impactotecnologico.challenge.pooling.car.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@AllArgsConstructor
@NoArgsConstructor
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
}
