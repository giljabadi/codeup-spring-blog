package com.codeup.springblog.repositories;

import com.codeup.springblog.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Long> {
    Ad findByTitle(String Title);
    Ad findFirstByTitle(String Title);

    //We can also define our own custom queries by following Spring's naming convention for queries. If we name our method the correct way, and define the right return type, Spring will automatically fill in the functionality for us.
}
