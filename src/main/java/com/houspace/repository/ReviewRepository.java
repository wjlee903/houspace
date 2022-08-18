package com.houspace.repository;

import com.houspace.entity.BoardEntity;
import com.houspace.entity.MemberEntity;
import com.houspace.entity.ReviewEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    //리뷰 등록 (member 종속)
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<ReviewEntity> findByBoard(BoardEntity board);

   // @Modifying
   // @Query("delete from ReviewEntity mr where mr.member := member")
   // Void deleteByMember(MemberEntity member);
}

