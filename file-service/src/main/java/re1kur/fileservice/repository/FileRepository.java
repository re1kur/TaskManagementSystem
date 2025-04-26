package re1kur.fileservice.repository;

import org.springframework.data.repository.CrudRepository;
import re1kur.fileservice.entity.File;

import java.util.UUID;

public interface FileRepository extends CrudRepository<File, UUID> {
}
