package impactotecnologico.challenge.pooling.car.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import impactotecnologico.challenge.pooling.car.models.Car;

@Service
public interface CarService {

	Optional<List<Car>> refreshCarsAvailability(List<Car> cars);

}
