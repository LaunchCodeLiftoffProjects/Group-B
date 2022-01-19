package org.launchcode.PostIt.models.data;

import org.launchcode.PostIt.models.AbstractPost;
import org.launchcode.PostIt.models.TextPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<TextPost, Integer> {
}