package re1kur.verificationservice.repository;

import org.springframework.data.repository.CrudRepository;
import re1kur.verificationservice.entity.Code;

public interface CodeRepository extends CrudRepository<Code, String> {
}
