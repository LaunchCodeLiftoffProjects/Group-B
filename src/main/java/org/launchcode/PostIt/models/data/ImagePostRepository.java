package org.launchcode.PostIt.models.data;

import org.launchcode.PostIt.models.ImagePost;
import org.launchcode.PostIt.models.TextPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagePostRepository extends CrudRepository<ImagePost, Integer> {
    Iterable<TextPost> findByUserId(int id);
}
