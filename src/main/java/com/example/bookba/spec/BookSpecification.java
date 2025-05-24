package com.example.bookba.spec;

import com.example.bookba.model.Book;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public class BookSpecification {
    public static Specification<Book> filter(
            String title, Integer publicationYear,
            List<Long> authorIds, List<Long> genreIds) {
        return (root, query, cb) -> {
            query.distinct(true);
            Predicate p = cb.conjunction();
            if (title != null && !title.isEmpty()) {
                p = cb.and(p, cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }
            if (publicationYear != null) {
                p = cb.and(p, cb.equal(root.get("publicationYear"), publicationYear));
            }
            if (authorIds != null && !authorIds.isEmpty()) {
                Join<Book, ?> join = root.join("authors", JoinType.INNER);
                p = cb.and(p, join.get("id").in(authorIds));
            }
            if (genreIds != null && !genreIds.isEmpty()) {
                Join<Book, ?> join = root.join("genres", JoinType.INNER);
                p = cb.and(p, join.get("id").in(genreIds));
            }
            return p;
        };
    }
}