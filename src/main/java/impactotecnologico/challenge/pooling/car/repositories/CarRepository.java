package impactotecnologico.challenge.pooling.car.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import impactotecnologico.challenge.pooling.car.models.Car;

@Repository
public interface CarRepository extends MongoRepository<Car, ObjectId> {
	
}
