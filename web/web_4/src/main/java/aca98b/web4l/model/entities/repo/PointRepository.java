package aca98b.web4l.model.entities.repo;

import aca98b.web4l.model.entities.Point;
import aca98b.web4l.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findAllByOwnerId(User entity);
    void deleteAllByOwnerId(User entity);
}