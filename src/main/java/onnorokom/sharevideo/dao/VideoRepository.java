package onnorokom.sharevideo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import onnorokom.sharevideo.entities.Video;

public interface VideoRepository extends JpaRepository<Video, Integer> {

}
