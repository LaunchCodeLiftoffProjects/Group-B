package org.launchcode.PostIt.models.data;


import org.launchcode.PostIt.models.TextPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<TextPost, Integer> {
    Iterable<TextPost> findByUserId(int id);
}
