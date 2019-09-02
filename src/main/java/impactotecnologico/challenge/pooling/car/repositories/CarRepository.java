package impactotecnologico.challenge.pooling.car.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import impactotecnologico.challenge.pooling.car.models.Car;
import impactotecnologico.challenge.pooling.car.models.Group;

@Repository
public interface CarRepository extends MongoRepository<Car, ObjectId> {

	Optional<Car> findOneBySeatsAvailables(int seatsRequested);

	Optional<Car> findOneByTravelers(Group travelers);
}
