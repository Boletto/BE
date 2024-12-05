package com.demoboletto.repository;

import com.demoboletto.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.demoboletto.domain.QPicture.picture;

@RequiredArgsConstructor
@Slf4j
public class PictureCustomRepositoryImpl implements PictureCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public void detachPicturesByUserId(Long userId) {
        queryFactory.update(picture)
                .where(picture.user.id.eq(userId))
                .set(picture.user, (User) null)
                .execute();
    }
}
